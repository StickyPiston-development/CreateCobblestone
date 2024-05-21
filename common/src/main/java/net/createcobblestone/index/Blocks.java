package net.createcobblestone.index;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class Blocks {

	public static final BlockEntry<CobblestoneGeneratorBlock> COBBLESTONE_GENERATOR_BLOCK_BLOCK_ENTRY = REGISTRATE.block("cobblestone_generator", CobblestoneGeneratorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.transform(BlockStressDefaults.setImpact(8))
			.tag(AllTags.AllBlockTags.SAFE_NBT.tag)
			.item()
			.transform(customItemModel())
			.register();

	public static void init() {
		CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);
	}
}
