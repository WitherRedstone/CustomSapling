package com.chinaex123.custom_sapling.data;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.init.CSItems;
import com.chinaex123.custom_sapling.init.tags.CSItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CustomSapling.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CSItemTags.SAPLINGS).add(CSItems.CUSTOM_SAPLING.get());
        tag(CSItemTags.CS_SAPLINGS).add(CSItems.CUSTOM_SAPLING.get());
    }
}
