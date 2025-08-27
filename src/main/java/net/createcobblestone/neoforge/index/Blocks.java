package net.createcobblestone.neoforge.index;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorBlock;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorBlockItem;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.createcobblestone.neoforge.CreateCobblestoneNeoForge.REGISTRATE;

public class Blocks {


    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                //.andThen(TooltipModifier.mapNull(CobblestoneType.create(item)))
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public static BlockEntry<MechanicalGeneratorBlock> MECHANICAL_GENERATOR_BLOCK;

    public static void register() {
        CreateCobblestoneNeoForge.LOGGER.info("Registering blocks for " + CreateCobblestoneNeoForge.NAME);

        int generator_stress;

        try {
            generator_stress = Config.common().generatorStress.get();
        } catch (IllegalStateException e) {
            // Fallback for forge config initializing late
            CreateCobblestoneNeoForge.LOGGER.warn("Set generator stress tooltip stress to 8. (config not initialized, this is a common forge problem)");
            generator_stress = 8;
        }

        // TODO: fix generator stress and tab
        MECHANICAL_GENERATOR_BLOCK = REGISTRATE.block("mechanical_generator", MechanicalGeneratorBlock::new)
                .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
//                .transform(BlockStressDefaults.setImpact(generator_stress))
                .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                .blockstate((ctx, prov) -> {
                    var model = prov.models().getExistingFile(prov.modLoc("block/mechanical_generator"));
                    prov.horizontalBlock(ctx.get(), model); // generates the 4 facing variants with y=0/90/180/270
                })
                .item(MechanicalGeneratorBlockItem::new)
                .model((ctx, prov) ->
                        prov.withExistingParent(ctx.getName(), prov.modLoc("block/mechanical_generator")))
                .build()
//                .tab(CreativeTabs.getBaseTabKey())
//                .transform(customItemModel())
                .register();
    }
}