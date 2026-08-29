package net.createcobblestone.index;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.MechanicalGeneratorBlock;
import net.createcobblestone.blocks.MechanicalGeneratorBlockItem;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class Blocks {

  static {
    REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
        // .andThen(TooltipModifier.mapNull(CobblestoneType.create(item)))
        .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
  }

  public static BlockEntry<MechanicalGeneratorBlock> MECHANICAL_GENERATOR_BLOCK;

  public static void init() {
    CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);

    int generator_stress;

    try {
      generator_stress = Config.common().generatorStress.get();
    } catch (IllegalStateException e) {
      // Fallback for forge config initializing late
      CreateCobblestoneMod.LOGGER
          .warn("Set generator stress tooltip stress to 8. (config not initialized, this is a common forge problem)");
      generator_stress = 8;
    }

    // TODO: fix generator stress and tab
    MECHANICAL_GENERATOR_BLOCK = REGISTRATE.block("mechanical_generator", MechanicalGeneratorBlock::new)
        .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
        // .transform(BlockStressDefaults.setImpact(generator_stress))
        .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
        .item(MechanicalGeneratorBlockItem::new)
        .tab(CreativeTabs.getBaseTabKey())
        .transform(customItemModel())
        .register();
  }
}
