package net.createcobblestone.index;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public final class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

    private final CreativeTabs.Tabs tab;

    public RegistrateDisplayItemsGenerator(CreativeTabs.Tabs tab) {
        this.tab = tab;
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output output) {
        ResourceKey<CreativeModeTab> tab = this.tab.getKey();

        List<ItemStack> stacks = new LinkedList<>();

        for (GeneratorType type: GeneratorType.values()){
            ItemStack stack = Blocks.MECHANICAL_GENERATOR_BLOCK.asStack();

            CompoundTag tag = new CompoundTag();
            tag.putString("type", type.name());
            stack.addTagElement("BlockEntityTag", tag);

            stacks.add(stack);

            CreateCobblestoneMod.LOGGER.info("Added {} generator to creative menu", type);
        }

        outputAll(output, stacks);
    }

    private static void outputAll(CreativeModeTab.Output output, List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getCount() != 1) {
                CreateCobblestoneMod.LOGGER.error("Invalid stack size {} for stack {}.", stack.getCount(), stack);
                continue;
            }
            output.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public record TabInfo(ResourceKey<CreativeModeTab> key, CreativeModeTab tab) {
    }
}
