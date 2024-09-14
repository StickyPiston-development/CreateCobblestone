package net.createcobblestone.index;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlock;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class Blocks {


    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                //.andThen(TooltipModifier.mapNull(CobblestoneType.create(item)))
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public static BlockEntry<CobblestoneGeneratorBlock> COBBLESTONE_GENERATOR_BLOCK;

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);

        int generator_stress;

        try {
            generator_stress = Config.common().generatorStress.get();
        } catch (IllegalStateException e) {
            // Fallback for forge config initializing late
            CreateCobblestoneMod.LOGGER.info("Set generator stress tooltip stress to 8.");
            generator_stress = 8;
        }

        COBBLESTONE_GENERATOR_BLOCK = REGISTRATE.block("cobblestone_generator", CobblestoneGeneratorBlock::new)
                .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                .transform(BlockStressDefaults.setImpact(generator_stress))
                .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                .item()
                .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                .transform(customItemModel())
                .register();
    }
}
