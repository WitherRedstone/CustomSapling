package com.chinaex123.custom_sapling.data.recipes;

import com.chinaex123.custom_sapling.data.builder.CustomSaplingRecipeBuilder;
import com.chinaex123.custom_sapling.init.CSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // 圆石树苗
        CustomSaplingRecipeBuilder.builder()
                .setSaplingId("cobblestone")
                .pattern("BBB", "BAB", "BBB")
                .defineTag('A', ItemTags.SAPLINGS)
                .define('B', Items.COBBLESTONE)
                .setResult(CSItems.CUSTOM_SAPLING.get())
                .save(recipeOutput);

        // 黑曜石树苗
        CustomSaplingRecipeBuilder.builder()
                .setSaplingId("obsidian")
                .pattern("BBB", "BAB", "BBB")
                .defineTag('A', ItemTags.SAPLINGS)
                .define('B', Items.OBSIDIAN)
                .setResult(CSItems.CUSTOM_SAPLING.get())
                .save(recipeOutput);

        // 铁矿石树苗
        CustomSaplingRecipeBuilder.builder()
                .setSaplingId("iron_ore")
                .pattern("BBB", "BAB", "BBB")
                .defineTag('A', ItemTags.SAPLINGS)
                .define('B', Items.IRON_ORE)
                .setResult(CSItems.CUSTOM_SAPLING.get())
                .save(recipeOutput);

        // 煤矿石树苗
        CustomSaplingRecipeBuilder.builder()
                .setSaplingId("coal_ore")
                .pattern("BBB", "BAB", "BBB")
                .defineTag('A', ItemTags.SAPLINGS)
                .define('B', Items.COAL_BLOCK)
                .setResult(CSItems.CUSTOM_SAPLING.get())
                .save(recipeOutput);

    }
}