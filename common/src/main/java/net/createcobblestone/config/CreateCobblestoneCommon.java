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

    public final ConfigInt maxStorage = i(256, "maxGeneratorStorage", Comments.maxStorage);

    public final ConfigGroup generatorsGroup = group(1, "generatorsEnabled", Comments.generatorsGroup);
    public final ConfigBool cobblestoneGeneratorEnabled = b(true, "cobblestoneGeneratorEnabled", Comments.cobblestoneGeneratorEnabled);
    public final ConfigBool basaltGeneratorEnabled = b(true, "basaltGeneratorEnabled", Comments.basaltGeneratorEnabled);
    public final ConfigBool stoneGeneratorEnabled = b(true, "stoneGeneratorEnabled", Comments.stoneGeneratorEnabled);
    public final ConfigBool limestoneGeneratorEnabled = b(true, "limestoneGeneratorEnabled", Comments.limestoneGeneratorEnabled);
    public final ConfigBool scoriaGeneratorEnabled = b(true, "scoriaGeneratorEnabled", Comments.scoriaGeneratorEnabled);

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

        public static String maxStorage = "Maximum storage of the generators (in items)";

        public static String[] generatorsGroup = new String[]{
                "Cobblestone generator types",
                "(Requires relaunch)"
        };
        public static String cobblestoneGeneratorEnabled = "Enable cobblestone generator";
        public static String basaltGeneratorEnabled = "Enable basalt generator";
        public static String stoneGeneratorEnabled = "Enable stone generator";
        public static String limestoneGeneratorEnabled = "Enable limestone generator";
        public static String scoriaGeneratorEnabled = "Enable stone generator";
    }

}
