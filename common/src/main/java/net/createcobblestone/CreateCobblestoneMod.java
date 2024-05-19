package net.createcobblestone;

import com.google.common.base.Suppliers;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCobblestoneMod {
    public static final String MOD_ID = "createcobblestone";
    public static final String NAME = "Create cobblestone";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateCobblestoneMod.MOD_ID);

    public static void init() {
        LOGGER.info("{} initializing! Create version: {} on platform: {}", NAME, Create.VERSION, CreateCobblestoneExpectPlatform.platformName());

        CreateCobblestoneBlocks.init(); // hold registrate in a separate class to avoid loading early on forge
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
