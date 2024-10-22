package net.createcobblestone.data.fabric;

import net.createcobblestone.data.GeneratorTypeLoader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class GeneratorTypeLoaderImpl {
    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            GeneratorTypeLoader.loadGeneratorTypes(server.getResourceManager());
        });
    }
}
