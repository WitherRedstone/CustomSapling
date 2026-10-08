package com.chinaex123.custom_sapling.data.provider;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.data.builder.CustomSaplingDefinitionBuilder;
import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CustomSaplingDefinitionProvider implements DataProvider {

    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final ExistingFileHelper existingFileHelper;
    private final Map<String, CustomSaplingDefinition> definitions = new HashMap<>();

    public CustomSaplingDefinitionProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries,
            ExistingFileHelper existingFileHelper
    ) {
        this.output = output;
        this.registries = registries;
        this.existingFileHelper = existingFileHelper;
    }

    protected void register() {

        // 圆石树苗
        definitions.put("cobblestone", CustomSaplingDefinitionBuilder.builder()
                .tintColor("#B3B3B3")
                .trunkBlock(Blocks.COBBLESTONE)
                .leavesBlock(Blocks.GRANITE)
                .trunkHeightUniform(3, 8)
                .foliageRadius(2)
                .trunkPlacer(new StraightTrunkPlacer(3, 3, 0))
                .foliagePlacer(new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2))
                .validGround(Blocks.COBBLESTONE)
                .build());

        // 黑曜石树苗
        definitions.put("obsidian", CustomSaplingDefinitionBuilder.builder()
                .tintColor("#5E0C6F")
                .trunkBlock(Blocks.OBSIDIAN)
                .leavesBlock(Blocks.MAGMA_BLOCK)
                .trunkHeightUniform(3, 8)
                .foliageRadius(2)
                .trunkPlacer(new StraightTrunkPlacer(3, 3, 0))
                .foliagePlacer(new PineFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2)))
                .validGround(Blocks.OBSIDIAN)
                .build());

        // 铁矿石树苗
        definitions.put("iron_ore", CustomSaplingDefinitionBuilder.builder()
                .tintColor("#D8AF93")
                .trunkBlock(Blocks.IRON_ORE)
                .leavesBlock(Blocks.STONE)
                .trunkHeightUniform(3, 8)
                .foliageRadius(2)
                .trunkPlacer(new ForkingTrunkPlacer(3, 3, 0))
                .foliagePlacer(new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(4)))
                .validGround(Blocks.IRON_ORE)
                .build());

        // 煤矿石树苗
        definitions.put("coal_ore", CustomSaplingDefinitionBuilder.builder()
                .tintColor("#494B3F")
                .trunkBlock(Blocks.COAL_ORE)
                .leavesBlock(Blocks.STONE)
                .trunkHeightUniform(5, 12)
                .foliageRadius(2)
                .trunkPlacer(new GiantTrunkPlacer(4, 4, 0))
                .foliagePlacer(new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2))
                .validGround(Blocks.COAL_ORE)
                .build());

    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cachedOutput) {
        register();

        PackOutput.PathProvider path = output.createPathProvider(
                PackOutput.Target.DATA_PACK, "saplings");

        return registries.thenCompose(registry -> CompletableFuture.allOf(
                definitions.entrySet().stream()
                        .map(entry -> {
                            ResourceLocation id = CustomSapling.id(entry.getKey());
                            return DataProvider.saveStable(
                                    cachedOutput, registry,
                                    CustomSaplingDefinition.CODEC,
                                    entry.getValue(),
                                    path.json(id));
                        })
                        .toArray(CompletableFuture[]::new)
        ));
    }

    @Override
    public @NotNull String getName() {
        return "[Custom Sapling]Custom Sapling Definitions: " + CustomSapling.MODID;
    }
}