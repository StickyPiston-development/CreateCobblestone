package net.createcobblestone.neoforge.index;

import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.createcobblestone.neoforge.index.RegistrateDisplayItemsGenerator.getTabObject;

public class CreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateCobblestoneNeoForge.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
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

    public enum Tabs {
        MAIN(CreativeTabs::getBaseTabKey);

        private final Supplier<ResourceKey<CreativeModeTab>> keySupplier;

        Tabs(Supplier<ResourceKey<CreativeModeTab>> keySupplier) {
            this.keySupplier = keySupplier;
        }

        public ResourceKey<CreativeModeTab> getKey() {
            return keySupplier.get();
        }

        public void use() {
            use(this);
        }

        public static void use(CreativeTabs.Tabs tab) {
            CreateCobblestoneNeoForge.REGISTRATE.setCreativeTab(getTabObject(tab.getKey()));
        }
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}