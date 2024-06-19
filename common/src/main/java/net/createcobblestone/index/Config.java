package net.createcobblestone.index;

import com.simibubi.create.foundation.config.ConfigBase;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.config.CreateCobblestoneCommon;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class Config {
    private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static CreateCobblestoneCommon common;

    public static CreateCobblestoneCommon common() {
        return common;
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

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

        ModConfigEvents.loading(CreateCobblestoneMod.MOD_ID).register(Config::onLoad);
        ModConfigEvents.reloading(CreateCobblestoneMod.MOD_ID).register(Config::onReload);
    }

    public static void onLoad(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig
                    .getSpec())
                config.onLoad();
    }

    public static void onReload(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig
                    .getSpec())
                config.onReload();
    }
}
