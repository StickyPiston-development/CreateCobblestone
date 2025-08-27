package net.createcobblestone.neoforge.index;

import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.createcobblestone.neoforge.data.GeneratorType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public final class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

    private final CreativeTabs.Tabs tab;

    public RegistrateDisplayItemsGenerator(CreativeTabs.Tabs tab) {
        this.tab = tab;
    }

    @Override
    public void accept(CreativeModeTab.@NotNull ItemDisplayParameters pParameters, CreativeModeTab.@NotNull Output output) {
        ResourceKey<CreativeModeTab> tab = this.tab.getKey();

        List<ItemStack> stacks = new LinkedList<>();

        ItemStack stack = Blocks.MECHANICAL_GENERATOR_BLOCK.asStack();

        GeneratorType.NONE.setTypeToItemStack(stack);

        stacks.add(stack);

        for (GeneratorType type: GeneratorType.getTypes()){

            if (type == GeneratorType.NONE) {
                continue;
            }

            stack = Blocks.MECHANICAL_GENERATOR_BLOCK.asStack();

            type.setTypeToItemStack(stack);

            stacks.add(stack);

            if (Config.common().enableDebugLogging.get()) {
                CreateCobblestoneNeoForge.LOGGER.info("Added {} generator to creative menu", type.getId());
            }
        }

        outputAll(output, stacks);
    }

    private static void outputAll(CreativeModeTab.Output output, List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.getCount() != 1) {
                CreateCobblestoneNeoForge.LOGGER.error("Invalid stack size {} for stack {}.", stack.getCount(), stack);
                continue;
            }
            output.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public record TabInfo(ResourceKey<CreativeModeTab> key, CreativeModeTab tab) {
    }

    public static DeferredHolder<CreativeModeTab,CreativeModeTab> getTabObject(ResourceKey<CreativeModeTab> tab) {
        DeferredHolder<CreativeModeTab,CreativeModeTab> tabObject;
        if (tab == CreativeTabs.getBaseTabKey()) {
            tabObject = CreativeTabs.MAIN_TAB;
        } else {
            tabObject = CreativeTabs.MAIN_TAB;
        }
        return tabObject;
    }
}
