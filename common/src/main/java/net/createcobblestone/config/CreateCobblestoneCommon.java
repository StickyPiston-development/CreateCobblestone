package net.createcobblestone.config;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.CClient;

public class CreateCobblestoneCommon extends ConfigBase {
    public final ConfigGroup common = group(0, "common",
            Comments.common);

    @Override
    public String getName() {
        return "common";
    }

    public final ConfigInt cobblestoneGeneratorStress = i(8, "cobblestoneGeneratorStress", Comments.cobblestoneGeneratorStress);
    public final ConfigInt cobblestoneGenratorRatio = i(8, "cobblestoneGeneratorRatio", Comments.cobblestoneGeneratorRatio);

    private static class Comments {
        public static String common = "Common settings";
        public static String[] cobblestoneGeneratorStress = new String[]{
                "Cobblestone generator stress",
                "stress * rpm = total stress"
        };
        public static String[] cobblestoneGeneratorRatio = new String[]{
                "Cobblestone generator ratio",
                "Cobblestone/tick = rpm/ratio"
        };
    }

}
