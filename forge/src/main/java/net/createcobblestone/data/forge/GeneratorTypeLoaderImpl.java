package net.createcobblestone.data.forge;

import net.createcobblestone.data.GeneratorTypeLoader;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GeneratorTypeLoaderImpl {
    public static void init() {}

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // Call your generator type loader here
        GeneratorTypeLoader.loadGeneratorTypes(event.getServer().getResourceManager());
    }
}
