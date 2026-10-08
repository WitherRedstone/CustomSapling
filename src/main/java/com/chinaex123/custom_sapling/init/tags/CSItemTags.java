package com.chinaex123.custom_sapling.init.tags;

import com.chinaex123.custom_sapling.CustomSapling;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CSItemTags {

    public static final TagKey<Item> SAPLINGS = mc("saplings");
    public static final TagKey<Item> CS_SAPLINGS = bind("saplings");

    private static TagKey<Item> mc(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CustomSapling.MODID, name));
    }
}
