package net.createcobblestone.neoforge.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.PacketDistributor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.createcobblestone.neoforge.CreateCobblestoneNeoForge.LOGGER;

public class GeneratorTypeLoader {

    public static boolean loaded = false;
    public static List<GeneratorTypesPayload.GeneratorTypePayload> loadedTypes = new ArrayList<>();

    public static void loadGeneratorTypes(ResourceManager resourceManager) {
        loaded = false;
        GeneratorType.init();

        loadedTypes.clear();

        LOGGER.info("Loading generator types from datapacks");

        Map<ResourceLocation, Resource> resources = resourceManager.listResources("generator_types", location -> location.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            try (InputStream inputStream = resource.open()) {

                JsonObject generatorJsonData = JsonParser.parseString(new String(inputStream.readAllBytes())).getAsJsonObject();

                String block = generatorJsonData.get("block").getAsString();

                if (BuiltInRegistries.BLOCK.get(ResourceLocation.parse(block)) == Blocks.AIR){
                    LOGGER.error("Generator type {} has no block assigned", id);
                }

                int generatorStress = -1;
                float outputPerSecondPerRpm = -1;
                int generatorStorage = -1;

                if (generatorJsonData.has("enabled") && !generatorJsonData.get("enabled").getAsBoolean()) {
                    // Enabled at default, but disabled when actively disabled. Completely removes the generator type, also from creative tab.
                    continue;
                }

                if (generatorJsonData.has("stress")) {
                    generatorStress = generatorJsonData.get("stress").getAsInt();
                }

                if (generatorJsonData.has("outputPerSecondPerRpm")) {
                    outputPerSecondPerRpm = generatorJsonData.get("outputPerSecondPerRpm").getAsFloat();
                }

                if (generatorJsonData.has("storage")) {
                    generatorStorage = generatorJsonData.get("storage").getAsInt();
                }

                // Deprecated
                if (generatorJsonData.has("ratio")) {

                    if (generatorJsonData.has("outputPerSecondPerRpm")) {
                        LOGGER.error("Generator type {} has both ratio and outputPerSecondPerRpm, outputPerSecondPerRpm will be used", id);
                    } else {
                        // Convert ratio to outputPerSecondPerRpm
                        LOGGER.warn("Generator type {} has deprecated ratio, please use outputPerSecondPerRpm instead. (Converted to {} outputPerSecondPerRpm)", id, 1/(generatorJsonData.get("ratio").getAsFloat())*20);

                        outputPerSecondPerRpm = 1/(generatorJsonData.get("ratio").getAsFloat())*20;
                    }
                }

                loadedTypes.add(new GeneratorTypesPayload.GeneratorTypePayload(id.toString(), block, generatorStress, outputPerSecondPerRpm, generatorStorage));

                GeneratorType.initializeNewType(id.toString(), ResourceLocation.parse(block), generatorStress, outputPerSecondPerRpm, generatorStorage);

            } catch (Exception e) {
                LOGGER.error("Error loading generator type: " + id, e);
            }
        }

        loaded = true;
        LOGGER.info("Generator types loading done");
    }

    public static void sendGeneratorTypesToClient(Iterable<ServerPlayer> players) {
        GeneratorTypesPayload payload = new GeneratorTypesPayload(List.copyOf(loadedTypes));

        int count = 0;
        for (ServerPlayer p : players) {
            PacketDistributor.sendToPlayer(p, payload);
            count++;
        }
        LOGGER.info("Sent generator update payload ({} entries) to {} player(s)",
                payload.list().size(), count);
    }

    public static void loadGeneratorTypesFromPacket(GeneratorTypesPayload payload) {
        LOGGER.info("Received generator update payload ({} entries)", payload.list().size());
        loaded = false;

        // Reset any client-side caches/registries
        GeneratorType.init();

        // Register each type locally on client
        for (GeneratorTypesPayload.GeneratorTypePayload g : payload.list()) {
            GeneratorType.initializeNewType(
                    g.id(),
                    ResourceLocation.parse(g.block()),
                    g.stress(),
                    g.ratio(),
                    g.storage()
            );
        }

        loaded = true;
    }

    public static void register() {}
}