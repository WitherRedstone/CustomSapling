package com.chinaex123.custom_sapling.block;

import com.chinaex123.custom_sapling.blockentity.CustomSaplingBlockEntity;
import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.chinaex123.custom_sapling.network.CSPacketHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class CustomSaplingBlock extends SaplingBlock implements EntityBlock {

    private static final String TAG_SAPLING_ID = "CustomSaplingId";

    public CustomSaplingBlock(BlockBehaviour.Properties properties) {
        super(
                new TreeGrower("custom_sapling", 0.0f,
                        Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty()),
                properties
        );
    }

    @Override
    public MapCodec<? extends SaplingBlock> codec() {
        return simpleCodec(CustomSaplingBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSaplingBlockEntity(pos, state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CustomSaplingBlockEntity saplingBE) {
            CustomSaplingDefinition def = saplingBE.getDefinition();
            if (def != null) {
                return def.canGrowOn(below);
            }
        }

        return true;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ResourceLocation saplingId = null;
        if (!level.isClientSide) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null) {
                CompoundTag tag = customData.copyTag();
                if (tag.contains(TAG_SAPLING_ID)) {
                    saplingId = ResourceLocation.tryParse(tag.getString(TAG_SAPLING_ID));
                }
            }
        }

        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide && saplingId != null
                && level.getBlockEntity(pos) instanceof CustomSaplingBlockEntity saplingBE) {
            saplingBE.setSaplingId(saplingId);

            if (level instanceof ServerLevel serverLevel) {
                CSPacketHandler.sendToTrackingPlayers(serverLevel, pos, saplingId.toString());

                if (!canSurvive(state, level, pos)) {
                    level.removeBlock(pos, false);
                    dropResources(state, level, pos);
                }
            }
        }
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            growCustomTree(level, pos, state, random);
        }
    }

    private void growCustomTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof CustomSaplingBlockEntity saplingBE)) return;

        CustomSaplingDefinition def = saplingBE.getDefinition();
        if (def == null) return;

        BlockState groundState = level.getBlockState(pos.below());

        TreeConfiguration config;
        try {
            config = def.buildTreeConfiguration(groundState);
        } catch (Exception e) {
            return;
        }

        level.removeBlock(pos, false);

        boolean success = Feature.TREE.place(
                new FeaturePlaceContext<>(
                        Optional.empty(), level,
                        level.getChunkSource().getGenerator(), random, pos, config
                )
        );

        if (!success) {
            level.setBlock(pos, state, 4);
        }
    }

}