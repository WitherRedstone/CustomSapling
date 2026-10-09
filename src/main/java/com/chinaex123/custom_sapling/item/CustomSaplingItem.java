package com.chinaex123.custom_sapling.item;

import com.chinaex123.custom_sapling.CustomSapling;
import com.chinaex123.custom_sapling.definition.CustomSaplingDefinition;
import com.chinaex123.custom_sapling.definition.GroundBlock;
import com.chinaex123.custom_sapling.data.recipes.CustomSaplingManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomSaplingItem extends BlockItem {

    private static final String TAG_SAPLING_ID = "CustomSaplingId";

    public CustomSaplingItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResourceLocation saplingId = getSaplingId(stack);
        if (saplingId != null) {
            return Component.translatable("block." + CustomSapling.MODID + "." + saplingId.getPath());
        }
        return super.getName(stack);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ResourceLocation saplingId = getSaplingId(context.getItemInHand());
        if (saplingId != null) {
            CustomSaplingDefinition def = CustomSaplingManager.INSTANCE.getDefinition(saplingId);
            if (def != null) {
                BlockState groundState = context.getLevel()
                        .getBlockState(context.getClickedPos());
                if (!def.canGrowOn(groundState)) {
                    return InteractionResult.FAIL;
                }
            }
        }
        return super.useOn(context);
    }

    public static void withSaplingId(ItemStack stack, ResourceLocation id) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = customData != null ? customData.copyTag() : new CompoundTag();

        tag.putString(TAG_SAPLING_ID, id.toString());

        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static ResourceLocation getSaplingId(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return null;

        CompoundTag tag = customData.copyTag();
        if (!tag.contains(TAG_SAPLING_ID)) return null;
        return ResourceLocation.tryParse(tag.getString(TAG_SAPLING_ID));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        ResourceLocation saplingId = getSaplingId(stack);
        if (saplingId == null) return;

        CustomSaplingDefinition def = CustomSaplingManager.INSTANCE.getDefinition(saplingId);
        if (def == null) return;

        List<GroundBlock> validBlocks = def.validGroundBlocks();
        String display;

        if (validBlocks.isEmpty()) {
            // 默认 dirt tag
            display = "#minecraft:dirt";
        } else if (validBlocks.size() == 1) {
            display = formatGroundBlock(validBlocks.get(0));
        } else if (validBlocks.size() == 2) {
            display = formatGroundBlock(validBlocks.get(0)) + "/" + formatGroundBlock(validBlocks.get(1));
        } else {
            String firstTwo = formatGroundBlock(validBlocks.get(0)) + "/" + formatGroundBlock(validBlocks.get(1));
            tooltipComponents.add(Component.translatable(
                    "tooltip." + CustomSapling.MODID + ".plant_on_more", firstTwo
            ).withStyle(style -> style.withColor(0x808080).withItalic(true)));
            return;
        }

        tooltipComponents.add(Component.translatable(
                "tooltip." + CustomSapling.MODID + ".plant_on", display
        ).withStyle(style -> style.withColor(0x808080).withItalic(true)));
    }

    private static String formatGroundBlock(GroundBlock gb) {
        if (gb.isTag()) {
            return "#" + gb.id();
        }
        ResourceLocation rl = ResourceLocation.tryParse(gb.id());
        if (rl == null) return gb.id();
        Block block = BuiltInRegistries.BLOCK.get(rl);
        if (block == Blocks.AIR) return gb.id();
        return Component.translatable(block.getDescriptionId()).getString();
    }

}