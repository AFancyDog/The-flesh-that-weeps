package dev.afancydog.fleshweep.Entities;

import dev.afancydog.fleshweep.Blocks.ModBlocks;
import dev.afancydog.fleshweep.TheFleshThatWeeps;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<DryingRackEntity> DRYING_RACK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TheFleshThatWeeps.MOD_ID,"drying_rack_entity"),
            FabricBlockEntityTypeBuilder.create(DryingRackEntity::new, ModBlocks.DRYING_RACK).build());

    public static void registerBlockEntities() {
    TheFleshThatWeeps.LOGGER.info("Registering entities for Fleshweep");
    };
}
