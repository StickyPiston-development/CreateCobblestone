package net.createcobblestone.blocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MechanicalGeneratorBlockItem extends BlockItem {

    public MechanicalGeneratorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        if (stack.getTag() != null) {
            CompoundTag BET = stack.getTagElement("BlockEntityTag");

            if (BET == null) {
                return super.getName(stack);
            }

            return Component.translatable(
                    "block.createcobblestone.generators." + BET.getString("type").toLowerCase()
            );
        }
        return super.getName(stack);
    }
}
