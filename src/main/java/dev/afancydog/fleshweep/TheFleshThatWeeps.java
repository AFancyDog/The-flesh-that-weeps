package dev.afancydog.fleshweep;

import dev.afancydog.fleshweep.Blocks.ModBlocks;
import dev.afancydog.fleshweep.Entities.ModBlockEntities;
import dev.afancydog.fleshweep.Items.Items;
import dev.afancydog.fleshweep.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheFleshThatWeeps implements ModInitializer {
	public static final String MOD_ID = "fleshweep";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Items.registerItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}