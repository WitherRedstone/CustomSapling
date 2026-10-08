package com.chinaex123.custom_sapling.network;

import com.chinaex123.custom_sapling.CustomSapling;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SaplingIdSyncPacket(BlockPos blockPos, String saplingId) implements CustomPacketPayload {

    public static final Type<SaplingIdSyncPacket> TYPE = new Type<>(CustomSapling.id("sapling_id_sync"));

    public static final StreamCodec<FriendlyByteBuf, SaplingIdSyncPacket> CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeBlockPos(packet.blockPos());
                        buf.writeUtf(packet.saplingId());
                    },
                    (buf) -> new SaplingIdSyncPacket(
                            buf.readBlockPos(),
                            buf.readUtf()
                    )
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}