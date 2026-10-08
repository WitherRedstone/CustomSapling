package com.chinaex123.custom_sapling.init;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.blockentity.CustomSaplingBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CSBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CustomSapling.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CustomSaplingBlockEntity>> CUSTOM_SAPLING =
            BLOCK_ENTITIES.register("custom_sapling",
                    () -> BlockEntityType.Builder.of(
                            CustomSaplingBlockEntity::new,
                            CSBlocks.CUSTOM_SAPLING.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
