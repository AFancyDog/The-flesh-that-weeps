package dev.afancydog.fleshweep.screen;

import dev.afancydog.fleshweep.TheFleshThatWeeps;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<DryingRackScreenHandler> DRYING_RACK_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,new Identifier(TheFleshThatWeeps.MOD_ID,"drying"),
            new ExtendedScreenHandlerType<>(DryingRackScreenHandler::new));


    public static void registerScreenHandlers() {
        TheFleshThatWeeps.LOGGER.info("Loading Screens");
    }
}
