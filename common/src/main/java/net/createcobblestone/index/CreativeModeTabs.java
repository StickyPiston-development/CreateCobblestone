package net.createcobblestone.index;


import dev.architectury.registry.CreativeTabRegistry;
import net.createcobblestone.CreateCobblestoneMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabs {

    public static final CreativeModeTab CREATECOBBLESTONE_TAB = CreativeTabRegistry.create(Component.translatable("itemGroup.createcobblestone"), Blocks.COBBLESTONE_GENERATOR_BLOCK_BLOCK_ENTRY::asStack);

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering creative tabs for " + CreateCobblestoneMod.NAME);
    }
}
