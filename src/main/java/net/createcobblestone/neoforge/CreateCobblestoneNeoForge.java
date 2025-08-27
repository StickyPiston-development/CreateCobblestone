package net.createcobblestone.neoforge;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorBlockEntity;
import net.createcobblestone.neoforge.data.GeneratorTypeLoader;
import net.createcobblestone.neoforge.data.provider.MechanicalGeneratorLootTableProvider;
import net.createcobblestone.neoforge.data.provider.MechanicalGeneratorRecipeProvider;
import net.createcobblestone.neoforge.data.GeneratorTypesPayload;
import net.createcobblestone.neoforge.data.ResourceReloadListener;
import net.createcobblestone.neoforge.index.*;
import net.createcobblestone.neoforge.index.Config;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@EventBusSubscriber(modid = CreateCobblestoneNeoForge.MOD_ID)
@Mod(CreateCobblestoneNeoForge.MOD_ID)
public class CreateCobblestoneNeoForge {
    public static final String MOD_ID = "createcobblestone";
    public static final String NAME = "Create cobblestone NeoForge";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public CreateCobblestoneNeoForge(IEventBus eventBus, ModContainer modContainer) {
        LOGGER.info("{} initializing on platform: {}", NAME, "NeoForge");

        REGISTRATE.registerEventListeners(eventBus);
        CreativeTabs.register(eventBus);
        NeoForge.EVENT_BUS.addListener(this::onDatapackSyncEvent);
        Config.register(modContainer);
        Blocks.register(); // hold registrate in a separate class to avoid loading early on forge
        BlockEntities.register();
        GeneratorTypeLoader.register();
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onGatherDataEvent(GatherDataEvent event) {
        var gen   = event.getGenerator();
        var out   = gen.getPackOutput();
        var loot  = event.getLookupProvider();
        var efh = event.getExistingFileHelper();
        var registries = event.getLookupProvider();


        // Recipe
        gen.addProvider(event.includeServer(), new MechanicalGeneratorRecipeProvider(out, loot));

        // Loot Table
        gen.addProvider(event.includeServer(), new MechanicalGeneratorLootTableProvider(out, registries));

    }

    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        // Register the custom resource reload listener
        event.addListener(new ResourceReloadListener());
    }


    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event){
        MechanicalGeneratorBlockEntity.registerCapabilities(event);
    }

    @SubscribeEvent
    public static void onModConfigLoadingEvent(ModConfigEvent.Loading event){
        Config.onLoad(event.getConfig());
    }

    @SubscribeEvent
    public static void onModConfigReloadingEvent(ModConfigEvent.Reloading event){
        Config.onReload(event.getConfig());
    }

    @SubscribeEvent
    public static void onRegisterPayloadsEvent(RegisterPayloadHandlersEvent event) {
        LOGGER.info("Registering packets for " + NAME);

        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                GeneratorTypesPayload.TYPE,
                GeneratorTypesPayload.STREAM_CODEC,
                Network::handleGeneratorTypesOnClient
        );
    }

    private void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            LOGGER.info("Syncing generator types to new client");
            GeneratorTypeLoader.sendGeneratorTypesToClient(
                    Collections.singleton(event.getPlayer())
            );

        } else if (!event.getPlayerList().getPlayers().isEmpty()) {
            LOGGER.info("Syncing generator types to all clients");
            GeneratorTypeLoader.sendGeneratorTypesToClient(
                    event.getPlayerList().getPlayers()
            );

        } else {
            LOGGER.warn("Syncing generator types, but no players found");
        }
    }
}
