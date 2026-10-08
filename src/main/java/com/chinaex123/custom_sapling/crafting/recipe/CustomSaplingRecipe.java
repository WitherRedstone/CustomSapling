package com.chinaex123.custom_sapling.crafting.recipe;

import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
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
        super("", CraftingBookCategory.MISC, ShapedRecipePattern.of(toCharKey(key), pattern), result);
        this.saplingId = saplingId;
        this.pattern = pattern;
        this.key = key;
        this.result = result;
    }

    private static Map<Character, Ingredient> toCharKey(Map<String, Ingredient> key) {
        Map<Character, Ingredient> charKey = new HashMap<>();
        for (Map.Entry<String, Ingredient> e : key.entrySet()) {
            charKey.put(e.getKey().charAt(0), e.getValue());
        }
        return charKey;
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
        return result;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack out = super.assemble(input, registries);
        CustomSaplingItem.withSaplingId(out, saplingId);
        return out;
    }
}