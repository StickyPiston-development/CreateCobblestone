package net.createcobblestone.index;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.data.GeneratorTypeLoader;
import net.minecraft.resources.ResourceLocation;

import static net.createcobblestone.CreateCobblestoneMod.LOGGER;

public class Network {
    public static final ResourceLocation GENERATOR_TYPES_PACKET = new ResourceLocation(CreateCobblestoneMod.MOD_ID, "generator_types_packet");

    public static void init() {
        LOGGER.info("Registering packets for " + CreateCobblestoneMod.NAME);

        // Client side only
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.serverToClient(), GENERATOR_TYPES_PACKET, GeneratorTypeLoader::loadGeneratorTypesFromPacket);
        }
    }
}
