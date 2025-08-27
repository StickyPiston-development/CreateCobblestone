package net.createcobblestone.neoforge.index;

import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.createcobblestone.neoforge.data.GeneratorTypeLoader;
import net.createcobblestone.neoforge.data.GeneratorTypesPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class Network {
    public static final ResourceLocation GENERATOR_TYPES_PACKET = CreateCobblestoneNeoForge.asResource("generator_types_packet");

    public static void handleGeneratorTypesOnClient(final GeneratorTypesPayload payload, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> GeneratorTypeLoader.loadGeneratorTypesFromPacket(payload));
    }
}
