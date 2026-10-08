package com.chinaex123.custom_sapling.init;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.block.CustomSaplingBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CSBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CustomSapling.MODID);

    public static final DeferredBlock<CustomSaplingBlock> CUSTOM_SAPLING = BLOCKS.register("custom_sapling",
            () -> new CustomSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
//    public static final DeferredBlock<CustomSaplingBlock> CUSTOM_SAPLING = BLOCKS.register("custom_sapling",
//            () -> new CustomSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).noCollission().instabreak().lightLevel(state -> 0)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}