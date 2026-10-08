package com.chinaex123.custom_sapling.network;

import com.chinaex123.custom_sapling.blockentity.CustomSaplingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class CSPacketHandler {
    private CSPacketHandler() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(RegisterPayloadHandlersEvent.class, event ->
                event.registrar("1.0").playToClient(SaplingIdSyncPacket.TYPE, SaplingIdSyncPacket.CODEC, CSPacketHandler::handleSaplingIdSync)
        );
    }

    public static void sendToTrackingPlayers(ServerLevel level, BlockPos pos, String saplingId) {
        PacketDistributor.sendToPlayersTrackingChunk(
                level,
                level.getChunk(pos).getPos(),
                new SaplingIdSyncPacket(pos, saplingId)
        );
    }

    private static void handleSaplingIdSync(SaplingIdSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            var level = context.player().level();
            BlockPos pos = packet.blockPos();
            var be = level.getBlockEntity(pos);

            if (be instanceof CustomSaplingBlockEntity saplingBE) {
                ResourceLocation id = ResourceLocation.tryParse(packet.saplingId());
                if (id != null) {
                    saplingBE.setSaplingId(id);
                    level.sendBlockUpdated(pos, saplingBE.getBlockState(), saplingBE.getBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                }
            }
        });
    }
}