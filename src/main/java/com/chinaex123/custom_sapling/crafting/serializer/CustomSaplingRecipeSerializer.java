package com.chinaex123.custom_sapling.crafting.serializer;

import com.chinaex123.custom_sapling.crafting.recipe.CustomSaplingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSaplingRecipeSerializer implements RecipeSerializer<CustomSaplingRecipe> {

    private static final MapCodec<CustomSaplingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("sapling_id")
                    .forGetter(CustomSaplingRecipe::getSaplingId),
            Codec.list(Codec.string(1, 3)).fieldOf("pattern")
                    .forGetter(CustomSaplingRecipe::getPattern2),
            Codec.unboundedMap(Codec.STRING, Ingredient.CODEC_NONEMPTY).fieldOf("key")
                    .forGetter(CustomSaplingRecipe::getKey2),
            ItemStack.CODEC.fieldOf("result")
                    .forGetter(CustomSaplingRecipe::getResult2)
    ).apply(instance, (saplingId, pattern, key, result) ->
            new CustomSaplingRecipe(null, saplingId, pattern, key, result)));

    private static final StreamCodec<RegistryFriendlyByteBuf, List<String>> PATTERN_CODEC = StreamCodec.of(
            (buf, list) -> {
                buf.writeVarInt(list.size());
                for (String s : list) {
                    ByteBufCodecs.STRING_UTF8.encode(buf, s);
                }
            },
            buf -> {
                int size = buf.readVarInt();
                List<String> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    list.add(ByteBufCodecs.STRING_UTF8.decode(buf));
                }
                return list;
            }
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, Map<String, Ingredient>> KEY_CODEC = StreamCodec.of(
            (buf, map) -> {
                buf.writeVarInt(map.size());
                for (Map.Entry<String, Ingredient> e : map.entrySet()) {
                    ByteBufCodecs.STRING_UTF8.encode(buf, e.getKey());
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, e.getValue());
                }
            },
            buf -> {
                int size = buf.readVarInt();
                Map<String, Ingredient> map = new HashMap<>(size);
                for (int i = 0; i < size; i++) {
                    String key = ByteBufCodecs.STRING_UTF8.decode(buf);
                    Ingredient value = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                    map.put(key, value);
                }
                return map;
            }
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, CustomSaplingRecipe> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, CustomSaplingRecipe::getSaplingId,
            PATTERN_CODEC, CustomSaplingRecipe::getPattern2,
            KEY_CODEC, CustomSaplingRecipe::getKey2,
            ItemStack.STREAM_CODEC, CustomSaplingRecipe::getResult2,
            (saplingId, pattern, key, result) ->
                    new CustomSaplingRecipe(null, saplingId, pattern, key, result)
    );

    @Override
    public MapCodec<CustomSaplingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CustomSaplingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}