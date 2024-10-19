package net.createcobblestone.index.forge;

import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.index.Blocks;
import net.createcobblestone.index.CreativeTabs;
import net.createcobblestone.index.RegistrateDisplayItemsGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabsImpl {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateCobblestoneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_cobblestone"))
                    .icon(() -> Blocks.MECHANICAL_GENERATOR_BLOCK.asStack())
                    .displayItems(new RegistrateDisplayItemsGenerator(CreativeTabs.Tabs.MAIN))
                    .build());

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.get();
    }

    public static ResourceKey<CreativeModeTab> getBaseTabKey() {
        return MAIN_TAB.getKey();
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
