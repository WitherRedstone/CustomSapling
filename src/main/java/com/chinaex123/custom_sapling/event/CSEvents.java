package com.chinaex123.custom_sapling.event;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.block.CustomSaplingBlock;
import com.chinaex123.custom_sapling.blockentity.CustomSaplingBlockEntity;
import com.chinaex123.custom_sapling.data.recipes.CustomSaplingManager;
import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@EventBusSubscriber(modid = CustomSapling.MODID)
public class CSEvents {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(CustomSaplingManager.INSTANCE);
    }

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        BlockEntity be = event.getBlockEntity();
        if (!(be instanceof CustomSaplingBlockEntity saplingBE)) return;
        if (!(event.getState().getBlock() instanceof CustomSaplingBlock)) return;

        ResourceLocation saplingId = saplingBE.getSaplingId();
        if (saplingId == null) return;

        for (var itemEntity : event.getDrops()) {
            ItemStack drop = itemEntity.getItem();
            if (drop.is(event.getState().getBlock().asItem())
                    && CustomSaplingItem.getSaplingId(drop) == null) {
                CustomSaplingItem.withSaplingId(drop, saplingId);
                itemEntity.setItem(drop);
            }
        }
    }
}