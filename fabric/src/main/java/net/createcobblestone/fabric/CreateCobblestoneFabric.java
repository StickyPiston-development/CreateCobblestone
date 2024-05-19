package net.createcobblestone.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.createcobblestone.CreateCobblestoneBlocks;
import net.createcobblestone.CreateCobblestoneMod;
import net.fabricmc.api.ModInitializer;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class CreateCobblestoneFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateCobblestoneMod.init();
        CreateCobblestoneMod.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), CreateCobblestoneMod.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        REGISTRATE.register();
    }
}
