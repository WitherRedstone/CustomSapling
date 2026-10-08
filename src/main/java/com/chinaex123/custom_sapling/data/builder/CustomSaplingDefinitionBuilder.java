package com.chinaex123.custom_sapling.data.builder;

import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.chinaex123.custom_sapling.definition.GroundBlock;
import com.chinaex123.custom_sapling.util.ColorUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.ArrayList;
import java.util.List;

public class CustomSaplingDefinitionBuilder {

    private int saplingTintColor = CustomSaplingDefinition.DEFAULT_TINT_COLOR;
    private BlockState trunkBlock;
    private BlockState leavesBlock;
    private IntProvider trunkHeight;
    private int foliageRadius = 3;
    private TrunkPlacer trunkPlacer;
    private FoliagePlacer foliagePlacer;
    private final List<GroundBlock> validGroundBlocks = new ArrayList<>();

    public static CustomSaplingDefinitionBuilder builder() {
        return new CustomSaplingDefinitionBuilder();
    }

    public CustomSaplingDefinitionBuilder tintColor(int color) {
        this.saplingTintColor = color;
        return this;
    }

    public CustomSaplingDefinitionBuilder tintColor(String hex) {
        this.saplingTintColor = ColorUtils.parseColor(hex);
        if (this.saplingTintColor < 0) {
            throw new IllegalArgumentException("[Custom Sapling]无效的颜色格式: " + hex);
        }
        return this;
    }

    public CustomSaplingDefinitionBuilder trunkBlock(Block block) {
        this.trunkBlock = block.defaultBlockState();
        return this;
    }

    public CustomSaplingDefinitionBuilder trunkBlock(BlockState state) {
        this.trunkBlock = state;
        return this;
    }

    public CustomSaplingDefinitionBuilder leavesBlock(Block block) {
        this.leavesBlock = block.defaultBlockState();
        return this;
    }

    public CustomSaplingDefinitionBuilder leavesBlock(BlockState state) {
        this.leavesBlock = state;
        return this;
    }

    public CustomSaplingDefinitionBuilder trunkHeight(IntProvider provider) {
        this.trunkHeight = provider;
        return this;
    }

    public CustomSaplingDefinitionBuilder trunkHeightUniform(int min, int max) {
        this.trunkHeight = UniformInt.of(min, max);
        return this;
    }

    public CustomSaplingDefinitionBuilder foliageRadius(int radius) {
        this.foliageRadius = radius;
        return this;
    }

    public CustomSaplingDefinitionBuilder trunkPlacer(TrunkPlacer placer) {
        this.trunkPlacer = placer;
        return this;
    }

    public CustomSaplingDefinitionBuilder foliagePlacer(FoliagePlacer placer) {
        this.foliagePlacer = placer;
        return this;
    }

    public CustomSaplingDefinitionBuilder validGround(String blockId) {
        this.validGroundBlocks.add(GroundBlock.parse(blockId));
        return this;
    }

    public CustomSaplingDefinitionBuilder validGround(Block block) {
        this.validGroundBlocks.add(new GroundBlock(
                BuiltInRegistries.BLOCK.getKey(block).toString(), false));
        return this;
    }

    public CustomSaplingDefinitionBuilder validGroundTag(String tagId) {
        String id = tagId.startsWith("#") ? tagId.substring(1) : tagId;
        this.validGroundBlocks.add(new GroundBlock(id, true));
        return this;
    }

    public CustomSaplingDefinition build() {
        if (trunkBlock == null) throw new IllegalStateException("[Custom Sapling]trunkBlock 未设置");
        if (leavesBlock == null) throw new IllegalStateException("[Custom Sapling]leavesBlock 未设置");
        if (trunkHeight == null) throw new IllegalStateException("[Custom Sapling]trunkHeight 未设置");
        if (trunkPlacer == null) throw new IllegalStateException("[Custom Sapling]trunkPlacer 未设置");
        if (foliagePlacer == null) throw new IllegalStateException("[Custom Sapling]foliagePlacer 未设置");

        return new CustomSaplingDefinition(
                saplingTintColor,
                trunkBlock,
                leavesBlock,
                trunkHeight,
                foliageRadius,
                trunkPlacer,
                foliagePlacer,
                List.copyOf(validGroundBlocks)
        );
    }
}