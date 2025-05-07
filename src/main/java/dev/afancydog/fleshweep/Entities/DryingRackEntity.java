package dev.afancydog.fleshweep.Entities;

import dev.afancydog.fleshweep.Items.Items;
import dev.afancydog.fleshweep.screen.DryingRackScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DryingRackEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3,ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT1 = 1;
    private static final int OUTPUT_SLOT2 = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxprogress = 72;



    public DryingRackEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRYING_RACK_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> DryingRackEntity.this.progress;
                    case 1 -> DryingRackEntity.this.maxprogress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0 -> DryingRackEntity.this.progress = value;
                    case 1 -> DryingRackEntity.this.maxprogress = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.fleshweep.drying_rack");
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("drying_rack-progress",progress);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        progress = nbt.getInt("drying_rack-progress");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT,1);
        ItemStack result = new ItemStack(Items.FLESH_CHUNK);
        this.setStack(OUTPUT_SLOT1, new ItemStack(result.getItem(),getStack(OUTPUT_SLOT1).getCount()+result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxprogress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(Items.FLESH_CHUNK);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == Items.FLESH_CHUNK;
        return hasInput && canInsertAmountIntoOutputSlot1(result) && canInsertAmountIntoOutputSlot2(result) && canInsertItemIntoOutputSlot1(result.getItem()) && canInsertItemIntoOutputSlot2(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot2(Item item) {
        return this.getStack(OUTPUT_SLOT2).getItem() == item || this.getStack(OUTPUT_SLOT2).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot2(ItemStack result) {
        return this.getStack(OUTPUT_SLOT2).getCount() + result.getCount() <= getStack(OUTPUT_SLOT2).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot1(Item item) {
        return this.getStack(OUTPUT_SLOT1).getItem() == item || this.getStack(OUTPUT_SLOT1).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot1(ItemStack result) {
        return this.getStack(OUTPUT_SLOT1).getCount() + result.getCount() <= getStack(OUTPUT_SLOT1).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT1).isEmpty() || this.getStack(OUTPUT_SLOT1).getCount() < this.getStack(OUTPUT_SLOT1).getMaxCount() && this.getStack(OUTPUT_SLOT2).isEmpty() || this.getStack(OUTPUT_SLOT2).getCount() < this.getStack(OUTPUT_SLOT2).getMaxCount();
    }


    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DryingRackScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }
}
