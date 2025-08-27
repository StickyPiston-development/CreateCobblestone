package net.createcobblestone.neoforge.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import static net.createcobblestone.neoforge.index.Network.GENERATOR_TYPES_PACKET;

public record GeneratorTypesPayload(List<GeneratorTypePayload> list) implements CustomPacketPayload {

    public record GeneratorTypePayload(String id, String block, int stress, float ratio, int storage) {
        public static final StreamCodec<FriendlyByteBuf, GeneratorTypePayload> STREAM_CODEC = StreamCodec.of(
                (buf, v) -> {
                    buf.writeUtf(v.id);
                    buf.writeUtf(v.block);
                    buf.writeVarInt(v.stress);
                    buf.writeFloat(v.ratio);
                    buf.writeVarInt(v.storage);
                },
                buf -> new GeneratorTypePayload(
                        buf.readUtf(),
                        buf.readUtf(),
                        buf.readVarInt(),
                        buf.readFloat(),
                        buf.readVarInt()
                )
        );
    }

    public static final CustomPacketPayload.Type<GeneratorTypesPayload> TYPE = new CustomPacketPayload.Type<>(GENERATOR_TYPES_PACKET);

    public static final StreamCodec<FriendlyByteBuf, GeneratorTypesPayload> STREAM_CODEC =
            StreamCodec.of(GeneratorTypesPayload::encode, GeneratorTypesPayload::decode);


    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private static void encode(FriendlyByteBuf buf, GeneratorTypesPayload payload) {
        List<GeneratorTypePayload> l = payload.list;
        buf.writeVarInt(l.size());
        for (GeneratorTypePayload gt : l) {
            GeneratorTypePayload.STREAM_CODEC.encode(buf, gt);
        }
    }

    private static GeneratorTypesPayload decode(FriendlyByteBuf buf) {
        int n = buf.readVarInt();
        List<GeneratorTypePayload> out = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            out.add(GeneratorTypePayload.STREAM_CODEC.decode(buf));
        }
        return new GeneratorTypesPayload(out);
    }

}
