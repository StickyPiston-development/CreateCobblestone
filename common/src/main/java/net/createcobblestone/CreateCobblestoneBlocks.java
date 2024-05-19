package net.createcobblestone;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.blocks.CobblestoneGeneratorBlock;
import net.minecraft.world.level.block.Block;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class CreateCobblestoneBlocks {

	public static final BlockEntry<CobblestoneGeneratorBlock> COBBLESTONE_GENERATOR_BLOCK_BLOCK_ENTRY = REGISTRATE.block("cobblestone_generator", CobblestoneGeneratorBlock::new).register();

	public static void init() {
		//REGISTRATE.register();
		CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);
	}
}
