package com.chinaex123.custom_sapling.init;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.crafting.recipe.CustomSaplingRecipe;
import com.chinaex123.custom_sapling.crafting.serializer.CustomSaplingRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber
public class CSRecipes {

    public static final CustomSaplingRecipeSerializer SERIALIZER = new CustomSaplingRecipeSerializer();

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CustomSapling.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SAPLING_SERIALIZER =
            RECIPE_SERIALIZERS.register("sapling_recipe", () -> SERIALIZER);

    public static RecipeType<CustomSaplingRecipe> SAPLING_RECIPE;

    @SubscribeEvent
    public static void register(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
            SAPLING_RECIPE = RecipeType.simple(CustomSapling.id("sapling_recipe"));
        }
    }
}