package com.chinaex123.custom_sapling.definition;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record GroundBlock(String id, boolean isTag) {

    public static GroundBlock parse(String s) {
        if (s.startsWith("#")) {
            return new GroundBlock(s.substring(1), true);
        }
        return new GroundBlock(s, false);
    }

    public static final Codec<GroundBlock> CODEC = Codec.STRING.xmap(
            GroundBlock::parse,
            gb -> gb.isTag ? "#" + gb.id() : gb.id()
    );

    public static final Codec<List<GroundBlock>> LIST_CODEC =
            Codec.list(CODEC);

    public boolean test(BlockState state) {
        if (isTag) {
            ResourceLocation rl = ResourceLocation.tryParse(id);
            if (rl == null) return false;
            return state.is(TagKey.create(BuiltInRegistries.BLOCK.key(), rl));
        }
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) return false;
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).equals(rl);
    }
}