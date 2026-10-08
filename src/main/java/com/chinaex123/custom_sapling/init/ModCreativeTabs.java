package com.chinaex123.custom_sapling.init;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.data.recipes.CustomSaplingManager;
import com.chinaex123.custom_sapling.item.CustomSaplingItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CustomSapling.MODID);

    public static final Supplier<CreativeModeTab> CUSTOM_SAPLING_TAB =
            CREATIVE_MODE_TAB.register("custom_sapling_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.OAK_SAPLING))
                    .title(Component.translatable("itemGroup.custom_sapling_tab"))
                    .displayItems((parameters, output) -> {

                        var all = CustomSaplingManager.INSTANCE.getAll();
                        if (!all.isEmpty()) {
                            for (Map.Entry<ResourceLocation, ?> entry : all.entrySet()) {
                                ItemStack sapling = new ItemStack(CSItems.CUSTOM_SAPLING.get());
                                CustomSaplingItem.withSaplingId(sapling, entry.getKey());
                                output.accept(sapling);
                            }
                        } else {
                            output.accept(CSItems.CUSTOM_SAPLING.get());
                        }

                    })

                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}