package com.chinaex123.custom_sapling.crafting.recipe;

import com.chinaex123.custom_sapling.init.CSRecipes;
import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSaplingRecipe extends ShapedRecipe {

    private final ResourceLocation saplingId;
    private final List<String> pattern;
    private final Map<String, Ingredient> key;
    private final ItemStack result;

    public CustomSaplingRecipe(ResourceLocation id, ResourceLocation saplingId, List<String> pattern, Map<String, Ingredient> key, ItemStack result) {
        super("", CraftingBookCategory.MISC, buildPattern(key, pattern), buildResult(result, saplingId));
        this.saplingId = saplingId;
        this.pattern = pattern;
        this.key = key;
        this.result = buildResult(result, saplingId);
    }

    private static ShapedRecipePattern buildPattern(Map<String, Ingredient> key, List<String> pattern) {
        Map<Character, Ingredient> charKey = new HashMap<>();
        for (Map.Entry<String, Ingredient> e : key.entrySet()) {
            charKey.put(e.getKey().charAt(0), e.getValue());
        }
        return ShapedRecipePattern.of(charKey, pattern);
    }

    private static ItemStack buildResult(ItemStack result, ResourceLocation saplingId) {
        ItemStack out = result.copy();
        CustomSaplingItem.withSaplingId(out, saplingId);
        return out;
    }

    private ItemStack withSaplingId(ItemStack stack) {
        return buildResult(stack, saplingId);
    }

    public ResourceLocation getSaplingId() {
        return saplingId;
    }

    public List<String> getPattern2() {
        return pattern;
    }

    public Map<String, Ingredient> getKey2() {
        return key;
    }

    public ItemStack getResult2() {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CSRecipes.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return withSaplingId(result);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return withSaplingId(result);
    }
}