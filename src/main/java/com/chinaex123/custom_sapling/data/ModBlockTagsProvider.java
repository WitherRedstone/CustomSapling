package com.chinaex123.custom_sapling.data;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.init.CSItems;
import com.chinaex123.custom_sapling.init.tags.CSBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CustomSapling.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CSBlockTags.SAPLINGS).add(CSItems.CUSTOM_SAPLING.get().getBlock());
        tag(CSBlockTags.CS_SAPLINGS).add(CSItems.CUSTOM_SAPLING.get().getBlock());
    }
}
