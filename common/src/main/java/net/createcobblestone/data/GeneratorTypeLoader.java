package net.createcobblestone.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.createcobblestone.index.Network;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Blocks;
import oshi.util.tuples.Quintet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.createcobblestone.CreateCobblestoneMod.LOGGER;

public class GeneratorTypeLoader {

    public static boolean loaded = false;
    public static List<Quintet<String, String, Integer, Float, Integer>> loadedTypes = new ArrayList<>();

    public static void loadGeneratorTypes(ResourceManager resourceManager) {
        loaded = false;
        GeneratorType.init();

        loadedTypes.clear();

        Map<ResourceLocation, Resource> resources = resourceManager.listResources("generator_types", location -> location.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            try (InputStream inputStream = resource.open()) {

                JsonObject generatorJsonData = JsonParser.parseString(new String(inputStream.readAllBytes())).getAsJsonObject();

                String block = generatorJsonData.get("block").getAsString();

                if (BuiltInRegistries.BLOCK.get(new ResourceLocation(block)) == Blocks.AIR){
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

                loadedTypes.add(new Quintet<>(id.toString(), block, generatorStress, outputPerSecondPerRpm, generatorStorage));

                GeneratorType.initializeNewType(id.toString(), new ResourceLocation(block), generatorStress, outputPerSecondPerRpm, generatorStorage);
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

                loadedTypes.add(new Quintet<>(id.toString(), block, generatorStress, outputPerSecondPerRpm, generatorStorage));

                GeneratorType.initializeNewType(id.toString(), new ResourceLocation(block), generatorStress, outputPerSecondPerRpm, generatorStorage);

            } catch (Exception e) {
                LOGGER.error("Error loading generator type: " + id, e);
            }
        }

        loaded = true;
        LOGGER.info("Generator types loading done");
    }

    public static void sendGeneratorTypesToClient(Iterable<ServerPlayer> players) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(loadedTypes.size());
        for (Quintet<String, String, Integer, Float, Integer> type: loadedTypes) {

            // id, block, stress, ratio, storage
            buf.writeUtf(type.getA());
            buf.writeUtf(type.getB());
            buf.writeInt(type.getC());
            buf.writeFloat(type.getD());
            buf.writeInt(type.getE());

        }

        NetworkManager.sendToPlayers(players, Network.GENERATOR_TYPES_PACKET, buf);
        LOGGER.info("Send generator update packet to client");
    }

    public static void loadGeneratorTypesFromPacket(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        LOGGER.info("Received generator update packet");
        loaded = false;

        GeneratorType.init();

        int generatorAmount = buf.readInt();

        for (int i = 0; i < generatorAmount; i++) {
            String id = buf.readUtf();
            String block = buf.readUtf();
            int generatorStress = buf.readInt();
            float generatorRatio = buf.readFloat();
            int generatorStorage = buf.readInt();

            GeneratorType.initializeNewType(id, new ResourceLocation(block), generatorStress, generatorRatio, generatorStorage);
        }

        loaded = true;
    }

    @ExpectPlatform
    public static void init() {}
}