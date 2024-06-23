package net.createcobblestone.index.fabric;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.index.Blocks;
import net.createcobblestone.index.CreativeTabs;
import net.createcobblestone.index.RegistrateDisplayItemsGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class CreativeTabsImpl {
    private static final RegistrateDisplayItemsGenerator.TabInfo MAIN_TAB = register("main",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup.create_cobblestone"))
                    .icon(() -> Blocks.COBBLESTONE_GENERATOR_BLOCK.asStack())
                    .displayItems(new RegistrateDisplayItemsGenerator(CreativeTabs.Tabs.MAIN))
                    .build());

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.tab();
    }

    public static ResourceKey<CreativeModeTab> getBaseTabKey() {
        return MAIN_TAB.key();
    }

    private static RegistrateDisplayItemsGenerator.TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
        ResourceLocation id = CreateCobblestoneMod.id(name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        CreativeModeTab tab = supplier.get();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
        return new RegistrateDisplayItemsGenerator.TabInfo(key, tab);
    }
}
