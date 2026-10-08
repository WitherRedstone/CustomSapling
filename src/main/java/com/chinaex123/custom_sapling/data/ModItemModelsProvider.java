package com.chinaex123.custom_sapling.data;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.init.CSBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CustomSapling.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(CSBlocks.CUSTOM_SAPLING);
    }

    private <T extends Block> void blockItem(DeferredBlock<T> block) {
        withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(CustomSapling.MODID, "block/" + block.getId().getPath()));
    }
}