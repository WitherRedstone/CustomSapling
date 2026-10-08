package com.chinaex123.custom_sapling.definition;

import com.chinaex123.custom_sapling.util.ColorUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;

public record CustomSaplingDefinition(
        int saplingTintColor,
        BlockState trunkBlock,
        BlockState leavesBlock,
        IntProvider trunkHeight,
        int foliageRadius,
        TrunkPlacer trunkPlacer,
        FoliagePlacer foliagePlacer,
        List<GroundBlock> validGroundBlocks
) {

    public static final int DEFAULT_TINT_COLOR = 0x19d234;

    private static final Codec<BlockState> BLOCK_STATE_CODEC = Codec.STRING.comapFlatMap(
            id -> {
                ResourceLocation rl = ResourceLocation.tryParse(id);
                if (rl == null) return DataResult.error(() -> "[Custom Sapling]无效的方块ID: " + id);
                Block block = BuiltInRegistries.BLOCK.get(rl);
                return DataResult.success(block.defaultBlockState());
            },
            state -> BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString()
    );

    public static final Codec<CustomSaplingDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ColorUtils.CODEC.orElse(DEFAULT_TINT_COLOR)
                    .fieldOf("sapling_tint_color")
                    .forGetter(CustomSaplingDefinition::saplingTintColor),

            BLOCK_STATE_CODEC.fieldOf("trunk_block")
                    .forGetter(CustomSaplingDefinition::trunkBlock),

            BLOCK_STATE_CODEC.fieldOf("leaves_block")
                    .forGetter(CustomSaplingDefinition::leavesBlock),

            IntProvider.CODEC.fieldOf("trunk_height")
                    .forGetter(CustomSaplingDefinition::trunkHeight),

            Codec.intRange(1, 8).orElse(3)
                    .fieldOf("foliage_radius")
                    .forGetter(CustomSaplingDefinition::foliageRadius),

            TrunkPlacer.CODEC.fieldOf("trunk_placer")
                    .forGetter(CustomSaplingDefinition::trunkPlacer),

            FoliagePlacer.CODEC.fieldOf("foliage_placer")
                    .forGetter(CustomSaplingDefinition::foliagePlacer),

            GroundBlock.LIST_CODEC.orElse(List.of())
                    .fieldOf("valid_ground_blocks")
                    .forGetter(CustomSaplingDefinition::validGroundBlocks)
    ).apply(instance, CustomSaplingDefinition::new));

    public boolean canGrowOn(BlockState state) {
        if (validGroundBlocks == null || validGroundBlocks.isEmpty()) {
            return state.is(BlockTags.DIRT);
        }
        for (GroundBlock gb : validGroundBlocks) {
            if (gb.test(state)) return true;
        }
        return false;
    }

    public TreeConfiguration buildTreeConfiguration(BlockState groundState) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(trunkBlock),
                trunkPlacer,
                BlockStateProvider.simple(leavesBlock),
                foliagePlacer,
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines()
                .dirt(BlockStateProvider.simple(groundState))
                .build();
    }
}