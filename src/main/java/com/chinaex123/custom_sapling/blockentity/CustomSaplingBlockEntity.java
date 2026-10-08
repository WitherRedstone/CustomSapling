package com.chinaex123.custom_sapling.blockentity;

import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.chinaex123.custom_sapling.init.CSBlockEntities;
import com.chinaex123.custom_sapling.data.recipes.CustomSaplingManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomSaplingBlockEntity extends BlockEntity {

    private static final String TAG_SAPLING_ID = "CustomSaplingId";

    private ResourceLocation saplingId;

    public CustomSaplingBlockEntity(BlockPos pos, BlockState state) {
        super(CSBlockEntities.CUSTOM_SAPLING.get(), pos, state);
    }

    public ResourceLocation getSaplingId() {
        return saplingId;
    }

    public void setSaplingId(ResourceLocation id) {
        this.saplingId = id;
        setChanged();
    }

    public CustomSaplingDefinition getDefinition() {
        if (saplingId == null) return null;
        return CustomSaplingManager.INSTANCE.getDefinition(saplingId);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (saplingId != null) {
            tag.putString(TAG_SAPLING_ID, saplingId.toString());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(TAG_SAPLING_ID)) {
            saplingId = ResourceLocation.tryParse(tag.getString(TAG_SAPLING_ID));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        if (saplingId != null) {
            tag.putString(TAG_SAPLING_ID, saplingId.toString());
        }
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}