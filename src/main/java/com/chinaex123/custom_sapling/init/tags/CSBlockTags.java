package com.chinaex123.custom_sapling.init.tags;

import com.chinaex123.custom_sapling.CustomSapling;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CSBlockTags {

    public static final TagKey<Block> SAPLINGS = mc("saplings");
    public static final TagKey<Block> CS_SAPLINGS = bind("saplings");

    private static TagKey<Block> mc(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

    private static TagKey<Block> bind(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(CustomSapling.MODID, name));
    }
}
