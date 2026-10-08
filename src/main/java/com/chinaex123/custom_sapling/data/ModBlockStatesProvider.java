//package com.chinaex123.custom_sapling.data;
//
//import com.chinaex123.custom_sapling.CustomSapling;
//import com.chinaex123.custom_sapling.init.CSBlocks;
//import net.minecraft.data.PackOutput;
//import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//
//public class ModBlockStatesProvider extends BlockStateProvider {
//
//    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
//        super(output, CustomSapling.MODID, exFileHelper);
//    }
//
//    @Override
//    protected void registerStatesAndModels() {
//        simpleBlock(CSBlocks.CUSTOM_SAPLING.get(),models().cross("custom_sapling", modLoc("block/custom_sapling")).renderType("cutout"));
//    }
//}