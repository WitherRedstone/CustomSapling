package com.chinaex123.custom_sapling;

import com.chinaex123.custom_sapling.event.CSEvents;
import com.chinaex123.custom_sapling.init.*;
import com.chinaex123.custom_sapling.network.CSPacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(CustomSapling.MODID)
public class CustomSapling {
    public static final String MODID = "custom_sapling";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CustomSapling(IEventBus modEventBus, ModContainer modContainer) {
        CSBlocks.register(modEventBus);
        CSBlockEntities.register(modEventBus);
        CSItems.register(modEventBus);
        CSRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        CSPacketHandler.register(modEventBus);

        NeoForge.EVENT_BUS.register(CSEvents.class);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(CustomSapling.MODID, name);
    }
}