package com.chinaex123.custom_sapling.data.recipes;

import com.chinaex123.custom_sapling.data.builder.CustomSaplingRecipeBuilder;
import com.chinaex123.custom_sapling.init.CSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // 圆石树苗：圆石围一圈 + 橡木树苗
        CustomSaplingRecipeBuilder.builder()
                .setSaplingId("cobblestone")
                .pattern("BBB", "BAB", "BBB")
                .define('A', Items.OAK_SAPLING)
                .define('B', Items.COBBLESTONE)
                .setResult(CSItems.CUSTOM_SAPLING.get())
                .save(recipeOutput);

    }
}