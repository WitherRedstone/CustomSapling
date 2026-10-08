package com.chinaex123.custom_sapling.compat.jei;

import com.chinaex123.custom_sapling.init.CSItems;
import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("custom_sapling", "jei");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(
                CSItems.CUSTOM_SAPLING.get(),
                new ISubtypeInterpreter<>() {
                    @Override
                    public Object getSubtypeData(ItemStack stack, UidContext uidContext) {
                        var saplingId = CustomSaplingItem.getSaplingId(stack);
                        return saplingId != null ? saplingId.toString() : null;
                    }

                    @Override
                    public @NotNull String getLegacyStringSubtypeInfo(ItemStack stack, UidContext uidContext) {
                        var saplingId = CustomSaplingItem.getSaplingId(stack);
                        return saplingId != null ? saplingId.toString() : "";
                    }
                }
        );
    }
}