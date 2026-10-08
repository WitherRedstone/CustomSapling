package com.chinaex123.custom_sapling.init;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CSItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CustomSapling.MODID);

    public static final DeferredItem<CustomSaplingItem> CUSTOM_SAPLING = ITEMS.register("custom_sapling",
            () -> new CustomSaplingItem(CSBlocks.CUSTOM_SAPLING.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}