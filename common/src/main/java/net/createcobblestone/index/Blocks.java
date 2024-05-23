package net.createcobblestone.index;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlock;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class Blocks {

	static {
		REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
	}

	public static final BlockEntry<CobblestoneGeneratorBlock> COBBLESTONE_GENERATOR_BLOCK_BLOCK_ENTRY = REGISTRATE.block("cobblestone_generator", CobblestoneGeneratorBlock::new)
			.initialProperties(AllBlocks.BRASS_CASING)
			.properties(p -> p.mapColor(MapColor.COLOR_BROWN))
			.transform(BlockStressDefaults.setImpact(8))
			.tag(AllTags.AllBlockTags.SAFE_NBT.tag)
			.item()
			.tab(AllCreativeModeTabs.BASE_CREATIVE_TAB.key())
			.transform(customItemModel())
			.register();

	public static void init() {
		CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);
	}
}
