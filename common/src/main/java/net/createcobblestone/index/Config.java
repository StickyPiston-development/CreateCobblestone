package net.createcobblestone.index;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.createcobblestone.config.CreateCobblestoneCommon;
import net.minecraftforge.fml.config.ModConfig;

import java.util.EnumMap;
import java.util.Map;

public class Config {
    public static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    protected static CreateCobblestoneCommon common;

    public static CreateCobblestoneCommon common() {
        return common;
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    public static boolean loaded = false;

    @ExpectPlatform
    public static void register() {
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
