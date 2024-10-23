package net.createcobblestone.forge;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.data.forge.ResourceReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateCobblestoneMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventBusSubscriber {

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        // Register the custom resource reload listener
        event.addListener(new ResourceReloadListener());
    }
}
