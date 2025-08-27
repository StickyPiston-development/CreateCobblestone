package net.createcobblestoneneoforge.index;

import net.createcobblestoneneoforge.CreateCobblestoneNeoForge;
import net.createcobblestoneneoforge.data.GeneratorTypeLoader;
import net.createcobblestoneneoforge.data.GeneratorTypesPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class Network {
    public static final ResourceLocation GENERATOR_TYPES_PACKET = CreateCobblestoneNeoForge.asResource("generator_types_packet");

    public static void handleGeneratorTypesOnClient(final GeneratorTypesPayload payload, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> GeneratorTypeLoader.loadGeneratorTypesFromPacket(payload));
    }
}
