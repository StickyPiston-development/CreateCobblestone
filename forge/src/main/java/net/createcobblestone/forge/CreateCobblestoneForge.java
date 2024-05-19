package net.createcobblestone.forge;

import net.createcobblestone.CreateCobblestoneBlocks;
import net.createcobblestone.CreateCobblestoneMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

@Mod(CreateCobblestoneMod.MOD_ID)
public class CreateCobblestoneForge {
    public CreateCobblestoneForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(eventBus);
        CreateCobblestoneMod.init();
    }
}
