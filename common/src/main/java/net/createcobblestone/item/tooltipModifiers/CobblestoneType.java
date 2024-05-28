package net.createcobblestone.item.tooltipModifiers;

import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.foundation.utility.Components;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CobblestoneType implements TooltipModifier {

    protected final Block block;

    public CobblestoneType(Block block){
        this.block = block;
    }

    @Nullable
    public static CobblestoneType create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();

            if (block instanceof CobblestoneGeneratorBlock) {
                return new CobblestoneType(block);
            }
        }
        return null;
    }

    @Override
    public void modify(ItemStack stack, Player player, TooltipFlag flags, List<Component> tooltip) {
        tooltip.add(Components.immutableEmpty());
        tooltip.add(Components.translatable(CreateCobblestoneMod.MOD_ID + ".tooltip.cobblestoneGeneratorType." + stack.getOrCreateTag().getString("type")).withStyle(ChatFormatting.DARK_GRAY));
    }
}
