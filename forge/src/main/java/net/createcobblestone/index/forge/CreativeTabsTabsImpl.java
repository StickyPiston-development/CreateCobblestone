package net.createcobblestone.index.forge;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.index.CreativeTabs;

import static net.createcobblestone.index.forge.RegistrateDisplayItemsGeneratorImpl.getTabObject;

public class CreativeTabsTabsImpl {
    public static void use(CreativeTabs.Tabs tab) {
        CreateCobblestoneMod.REGISTRATE.setCreativeTab(getTabObject(tab.getKey()));
    }
}
