package dev.afancydog.fleshweep.Items;

import dev.afancydog.fleshweep.TheFleshThatWeeps;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.util.Identifier;



public class Items {

    public static final Item FLESH_CHUNK = register("flesh_chunk",new Item(new FabricItemSettings()));


    public static Item register(String name,Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TheFleshThatWeeps.MOD_ID,name),item);
    }

    public static void registerItems() {
        TheFleshThatWeeps.LOGGER.info("Loading items for The Flesh that Weeps");
    }

}
