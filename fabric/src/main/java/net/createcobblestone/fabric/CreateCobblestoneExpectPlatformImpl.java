package net.createcobblestone.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class CreateCobblestoneExpectPlatformImpl {
	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}
}
