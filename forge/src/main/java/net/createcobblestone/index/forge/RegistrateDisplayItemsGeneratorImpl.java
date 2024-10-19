package net.createcobblestone.index.forge;

import net.createcobblestone.index.CreativeTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class RegistrateDisplayItemsGeneratorImpl {
    public static RegistryObject<CreativeModeTab> getTabObject(ResourceKey<CreativeModeTab> tab) {
        RegistryObject<CreativeModeTab> tabObject;
        if (tab == CreativeTabs.getBaseTabKey()) {
            tabObject = CreativeTabsImpl.MAIN_TAB;
        } else {
            tabObject = CreativeTabsImpl.MAIN_TAB;
        }
        return tabObject;
    }
}
