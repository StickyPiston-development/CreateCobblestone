package net.createcobblestone.index;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.createcobblestone.CreateCobblestoneMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(CreateCobblestoneMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> CREATECOBBLESTONE_TAB = TABS.register(
            CreateCobblestoneMod.MOD_ID, // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.create_cobblestone"), // Tab Name
                () -> new ItemStack(Blocks.COBBLESTONE_GENERATOR_BLOCK.get()) // Icon
            )
    );

    public static void register (){}
}
