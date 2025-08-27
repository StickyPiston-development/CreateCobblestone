package net.createcobblestone.neoforge.blocks;

import net.createcobblestone.neoforge.data.GeneratorType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MechanicalGeneratorBlockItem extends BlockItem {

    public MechanicalGeneratorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        CustomData beData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (beData  != null) {
            CompoundTag BET = beData.copyTag(); // safe copy of the tag

            Item generatedItem = GeneratorType.fromId(BET.getString(GeneratorType.TYPE_KEY))
                .getItem();

            if (generatedItem != Items.AIR) {
                tooltipComponents.add(
                        Component.translatable("block.createcobblestone.generators.hovertext.itemprefix")
                            .append(generatedItem.getName(generatedItem.getDefaultInstance()))
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
                );
            } else {
                tooltipComponents.add(
                        Component.translatable("block.createcobblestone.generators.hovertext.no_item")
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
                );
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack defaultStack = super.getDefaultInstance();

        GeneratorType.NONE.setTypeToItemStack(defaultStack);

        return defaultStack;
    }

    @Override
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
        GeneratorType.NONE.setTypeToItemStack(stack);
        super.onCraftedBy(stack, level, player);
    }
}