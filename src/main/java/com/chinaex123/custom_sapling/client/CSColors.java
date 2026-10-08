package com.chinaex123.custom_sapling.client;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.blockentity.CustomSaplingBlockEntity;
import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.chinaex123.custom_sapling.init.CSBlocks;
import com.chinaex123.custom_sapling.init.CSItems;
import com.chinaex123.custom_sapling.data.recipes.CustomSaplingManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = CustomSapling.MODID, value = Dist.CLIENT)
public class CSColors {

    private static final String TAG_SAPLING_ID = "CustomSaplingId";
    private static final int DEFAULT_COLOR = 0x3A7D44;

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (level != null && pos != null) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof CustomSaplingBlockEntity saplingBE) {
                    CustomSaplingDefinition def = saplingBE.getDefinition();
                    if (def != null) return def.saplingTintColor();
                }
            }
            return DEFAULT_COLOR;
        }, CSBlocks.CUSTOM_SAPLING.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            var customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null) {
                CompoundTag tag = customData.copyTag();
                if (tag.contains(TAG_SAPLING_ID)) {
                    ResourceLocation id = ResourceLocation.tryParse(tag.getString(TAG_SAPLING_ID));
                    if (id != null) {
                        CustomSaplingDefinition def = CustomSaplingManager.INSTANCE.getDefinition(id);
                        if (def != null) return def.saplingTintColor();
                    }
                }
            }
            return DEFAULT_COLOR;
        }, CSItems.CUSTOM_SAPLING.get());
    }
}