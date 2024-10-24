package net.createcobblestone.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.createcobblestone.CreateCobblestoneMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class GeneratorTypeLoader {

    public static void loadGeneratorTypes(ResourceManager resourceManager) {
        GeneratorType.init();

        Map<ResourceLocation, Resource> resources = resourceManager.listResources("generator_types", location -> location.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            try (InputStream inputStream = resource.open()) {

                JsonObject generatorJsonData = JsonParser.parseString(new String(inputStream.readAllBytes())).getAsJsonObject();

                String block = generatorJsonData.get("block").getAsString();

                int generatorStress = -1;
                float generatorRatio = -1;
                int generatorStorage = -1;

                if (generatorJsonData.has("stress")) {
                    generatorStress = generatorJsonData.get("stress").getAsInt();
                }

                if (generatorJsonData.has("ratio")) {
                    generatorRatio = generatorJsonData.get("ratio").getAsFloat();
                }

                if (generatorJsonData.has("storage")) {
                    generatorStorage = generatorJsonData.get("storage").getAsInt();
                }

                new GeneratorType(id.toString(), new ResourceLocation(block), generatorStress, generatorRatio, generatorStorage);
            } catch (IOException e) {
                CreateCobblestoneMod.LOGGER.error("Error loading generator type: " + id, e);
            }
        }

        CreateCobblestoneMod.LOGGER.info("Generator types loading done");
    }

    @ExpectPlatform
    public static void init() {}
}