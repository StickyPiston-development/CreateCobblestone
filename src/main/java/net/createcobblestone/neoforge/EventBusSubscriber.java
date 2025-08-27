package net.createcobblestone.neoforge;

import net.createcobblestone.neoforge.data.ResourceReloadListener;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@net.neoforged.fml.common.EventBusSubscriber(modid = CreateCobblestoneNeoForge.MOD_ID)
public class EventBusSubscriber {

    @net.neoforged.bus.api.SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        // Register the custom resource reload listener
        event.addListener(new ResourceReloadListener());
    }
}