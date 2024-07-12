package net.createcobblestone.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class CreateCobblestoneCommon extends ConfigBase {
    public final ConfigGroup common = group(0, "common",
            Comments.common);

    @Override
    public String getName() {
        return "common";
    }

    public final ConfigInt generatorStress = i(8, "generatorStress", Comments.generatorStress);
    public final ConfigFloat generatorRatio = f(8.0f, 0.01f, "generatorRatio", Comments.generatorRatio);

    public final ConfigGroup generatorsGroup = group(1, "generatorsEnabled", Comments.generatorsGroup);
    public final ConfigBool cobblestoneGeneratorEnabled = b(true, "cobblestoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool basaltGeneratorEnabled = b(true, "basaltGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool stoneGeneratorEnabled = b(true, "stoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool limestoneGeneratorEnabled = b(true, "limestoneGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool scoriaGeneratorEnabled = b(true, "scoriaGeneratorEnabled", Comments.generatorEnabled);

    public final ConfigBool deepslateGeneratorEnabled = b(false, "deepslateGeneratorEnabled", Comments.generatorEnabled);
    public final ConfigBool cobbledDeepslateGeneratorEnabled = b(false, "cobbledDeepslateGeneratorEnabled", Comments.generatorEnabled);

    private static class Comments {
        public static String common = "Common settings";
        public static String[] generatorStress = new String[]{
                "Cobblestone generator stress",
                "stress * rpm = total stress"
        };
        public static String[] generatorRatio = new String[]{
                "Cobblestone generator ratio",
                "Cobblestone/tick = rpm/ratio"
        };

        public static String[] generatorsGroup = new String[]{
                "Cobblestone generator types",
                "(Requires relaunch)"
        };
        public static String generatorEnabled = "Enables the generator. When disabled, the generator is fully removed from the game. Make a backup before doing this.";
    }

}
