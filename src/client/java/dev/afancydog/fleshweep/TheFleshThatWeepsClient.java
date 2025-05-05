package dev.afancydog.fleshweep;

import dev.afancydog.fleshweep.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class TheFleshThatWeepsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HandledScreens.register(ModScreenHandlers.DRYING_RACK_SCREEN_HANDLER, DryingRackScreen::new);
	}
}