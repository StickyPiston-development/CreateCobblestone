package net.createcobblestone.index.fabric;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.index.CreativeTabs;

public class CreativeTabsTabsImpl {
    public static void use(CreativeTabs.Tabs tab) {
        CreateCobblestoneMod.REGISTRATE.setCreativeTab(tab.getKey());
    }
}
