package net.createcobblestone.blocks;

import net.createcobblestone.data.GeneratorType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MechanicalGeneratorBlockItem extends BlockItem {

    public MechanicalGeneratorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (stack.getTag() != null) {
            CompoundTag BET = stack.getTagElement("BlockEntityTag");

            if (BET != null) {

                Item generatedItem = GeneratorType.fromId(
                        BET.getString("type")
                ).getItem();

                if (generatedItem != Items.AIR) {
                    tooltipComponents.add(
                            Component.translatable(
                                    "block.createcobblestone.generators.hovertext.itemprefix"
                            ).append(
                                    generatedItem.getName(generatedItem.getDefaultInstance())
                            ).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
                    );
                } else {
                    tooltipComponents.add(
                            Component.translatable(
                                    "block.createcobblestone.generators.hovertext.no_item"
                            ).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
                    );
                }



            }
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack defaultStack = super.getDefaultInstance();

        defaultStack.getOrCreateTagElement("BlockEntityTag").putString("type", GeneratorType.NONE.getId());

        return defaultStack;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.getOrCreateTagElement("BlockEntityTag").putString("type", GeneratorType.NONE.getId());
        super.onCraftedBy(stack, level, player);
    }
}
