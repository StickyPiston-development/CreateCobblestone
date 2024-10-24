package net.createcobblestone.config;

import com.simibubi.create.foundation.config.ConfigBase;
import net.createcobblestone.data.GeneratorType;
import net.createcobblestone.index.Config;

import java.util.Objects;

public class CreateCobblestoneCommon extends ConfigBase {
    public final ConfigGroup common = group(0, "common",
            Comments.common);

    @Override
    public String getName() {
        return "common";
    }

    public final ConfigInt generatorStress = i(8, 0, "generatorStress", Comments.generatorStress);
    public final ConfigFloat generatorRatio = f(8.0f, 0.01f, "generatorRatio", Comments.generatorRatio);

    public final ConfigInt maxStorage = i(256, 1, "maxGeneratorStorage", Comments.maxStorage);

    public final ConfigGroup generatorsGroup = group(1, "generatorsEnabled", Comments.generatorsGroup);
    public final ConfigBool cobblestoneGeneratorEnabled = b(true, "cobblestoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool basaltGeneratorEnabled = b(true, "basaltGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool stoneGeneratorEnabled = b(true, "stoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool limestoneGeneratorEnabled = b(true, "limestoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool scoriaGeneratorEnabled = b(true, "scoriaGeneratorEnabled", Comments.generatorEnabled);

    public final ConfigBool deepslateGeneratorEnabled = b(false, "deepslateGeneratorEnabled", Comments.deepslateGeneratorsEnabled);
    public final ConfigBool cobbledDeepslateGeneratorEnabled = b(false, "cobbledDeepslateGeneratorEnabled", Comments.deepslateGeneratorsEnabled);

    private static class Comments {
        public static String common = "Common config";
        public static String[] generatorStress = new String[]{
                "Default generator stress",
                "stress * rpm = total stress",
                "(Can be overridden by custom generator types)",
        };
        public static String[] generatorRatio = new String[]{
                "Default generator ratio",
                "Cobblestone/tick = rpm/ratio",
                "(Can be overridden by custom generator types)",
        };

        public static String[] maxStorage = new String[]{
                "Default maximum storage of the generators in items",
                "(Can be overridden by custom generator types)",
        };

        public static String[] generatorsGroup = new String[]{
                "Cobblestone generator types",
        };
        public static String generatorEnabled = "Enables the generator. When disabled, the generator is replaced with unset generators. Make a backup before doing this.";
        public static String[] deepslateGeneratorsEnabled = new String[]{
                Comments.generatorEnabled,
                "(Requires deepslate generators datapack, https://modrinth.com/datapack/create-cobblestone-deepslate-generators)"
        };
    }

    public boolean isEnabled(GeneratorType type) {

        if (type.equals(GeneratorType.NONE)){
            return true;
        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/cobblestone.json")) {
            return Config.common().cobblestoneGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/stone.json")) {
            return Config.common().stoneGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/basalt.json")) {
            return Config.common().basaltGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/limestone.json")) {
            return Config.common().limestoneGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/scoria.json")) {
            return Config.common().scoriaGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/deepslate.json")) {
            return Config.common().deepslateGeneratorEnabled.get();

        } else if (Objects.equals(type.getId(), "createcobblestone:generator_types/cobbled_deepslate.json")) {
            return Config.common().cobbledDeepslateGeneratorEnabled.get();
        }
        return true;
    }

}
