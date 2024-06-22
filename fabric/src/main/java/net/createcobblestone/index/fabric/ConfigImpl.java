package net.createcobblestone.index.fabric;

import com.simibubi.create.foundation.config.ConfigBase;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.config.CreateCobblestoneCommon;
import net.createcobblestone.index.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Supplier;

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

    public static void register() {
        common = register(CreateCobblestoneCommon::new, ModConfig.Type.COMMON);

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
            ForgeConfigRegistry.INSTANCE.register(CreateCobblestoneMod.MOD_ID, pair.getKey(), pair.getValue().specification);

        ModConfigEvents.loading(CreateCobblestoneMod.MOD_ID).register(net.createcobblestone.index.Config::onLoad);
        ModConfigEvents.reloading(CreateCobblestoneMod.MOD_ID).register(net.createcobblestone.index.Config::onReload);
    }
}
