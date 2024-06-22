package net.createcobblestone.index.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.config.CreateCobblestoneCommon;
import net.createcobblestone.index.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CreateCobblestoneMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigImpl extends Config {
    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    @SubscribeEvent
    public static void onLoadForge(ModConfigEvent.Loading event) {
        onLoad(event.getConfig());
    }

    @SubscribeEvent
    public static void onReloadForge(ModConfigEvent.Reloading event) {
        onReload(event.getConfig());
    }

    public static void register() {
        ModLoadingContext context = ModLoadingContext.get();
        common = register(CreateCobblestoneCommon::new, ModConfig.Type.COMMON);

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
            context.registerConfig(pair.getKey(), pair.getValue().specification);
    }
}
