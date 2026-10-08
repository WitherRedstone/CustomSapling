package com.chinaex123.custom_sapling.data.builder;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.crafting.recipe.CustomSaplingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomSaplingRecipeBuilder {

    private ResourceLocation saplingId;
    private final List<String> pattern = new ArrayList<>();
    private final Map<String, Ingredient> key = new LinkedHashMap<>();
    private ItemStack result;

    public static CustomSaplingRecipeBuilder builder() {
        return new CustomSaplingRecipeBuilder();
    }

    public static CustomSaplingRecipeBuilder sapling(ResourceLocation saplingId, ItemStack result) {
        CustomSaplingRecipeBuilder b = new CustomSaplingRecipeBuilder();
        b.saplingId = saplingId;
        b.result = result;
        return b;
    }

    public CustomSaplingRecipeBuilder setSaplingId(ResourceLocation saplingId) {
        this.saplingId = saplingId;
        return this;
    }

    public CustomSaplingRecipeBuilder setSaplingId(String saplingId) {
        this.saplingId = CustomSapling.id(saplingId);
        return this;
    }

    public CustomSaplingRecipeBuilder pattern(String row) {
        this.pattern.add(row);
        return this;
    }

    public CustomSaplingRecipeBuilder pattern(String... rows) {
        this.pattern.clear();
        this.pattern.addAll(List.of(rows));
        return this;
    }

    public CustomSaplingRecipeBuilder define(char c, Ingredient ingredient) {
        this.key.put(String.valueOf(c), ingredient);
        return this;
    }

    public CustomSaplingRecipeBuilder define(char c, Item item) {
        this.key.put(String.valueOf(c), Ingredient.of(item));
        return this;
    }

    public CustomSaplingRecipeBuilder define(char c, ResourceLocation itemId) {
        this.key.put(String.valueOf(c), Ingredient.of(BuiltInRegistries.ITEM.get(itemId)));
        return this;
    }

    public CustomSaplingRecipeBuilder setResult(ItemStack result) {
        this.result = result;
        return this;
    }

    public CustomSaplingRecipeBuilder setResult(Item item) {
        this.result = new ItemStack(item);
        return this;
    }

    public CustomSaplingRecipeBuilder setResult(ResourceLocation itemId) {
        this.result = new ItemStack(BuiltInRegistries.ITEM.get(itemId));
        return this;
    }

    public CustomSaplingRecipe build() {
        if (saplingId == null) {
            throw new IllegalStateException("[Custom Sapling]sapling_id 未设置");
        }
        if (pattern.isEmpty()) {
            throw new IllegalStateException("[Custom Sapling]pattern 未设置");
        }
        if (result == null) {
            throw new IllegalStateException("[Custom Sapling]result 未设置");
        }
        return new CustomSaplingRecipe(null, saplingId, List.copyOf(pattern), Map.copyOf(key), result);
    }

    public void save(RecipeOutput output) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                CustomSapling.MODID,
                "sapling_recipe/" + saplingId.getPath()
        );
        save(output, id);
    }

    public void save(RecipeOutput output, ResourceLocation id) {
        output.accept(id, build(), null);
    }

    public void save(RecipeOutput output, String name) {
        save(output, ResourceLocation.parse(name));
    }
}