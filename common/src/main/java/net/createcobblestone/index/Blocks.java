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
import net.createcobblestone.util.GeneratorType;
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
    public static BlockEntry<CobblestoneGeneratorBlock> STONE_GENERATOR_BLOCK;
    public static BlockEntry<CobblestoneGeneratorBlock> BASALT_GENERATOR_BLOCK;
    public static BlockEntry<CobblestoneGeneratorBlock> LIMESTONE_GENERATOR_BLOCK;
    public static BlockEntry<CobblestoneGeneratorBlock> SCORIA_GENERATOR_BLOCK;

    public static BlockEntry<CobblestoneGeneratorBlock> DEEPSLATE_GENERATOR_BLOCK;
    public static BlockEntry<CobblestoneGeneratorBlock> COBBLED_DEEPSLATE_GENERATOR_BLOCK;

//	public static BlockEntry<CobblestoneGeneratorBlock> of(GeneratorType type){
//		switch (type) {
//            case COBBLESTONE -> {
//                return COBBLESTONE_GENERATOR_BLOCK;
//            }
//            case STONE -> {
//                return STONE_GENERATOR_BLOCK;
//            }
//            case BASALT -> {
//                return BASALT_GENERATOR_BLOCK;
//            }
//            case LIMESTONE -> {
//                return LIMESTONE_GENERATOR_BLOCK;
//            }
//            case SCORIA -> {
//                return SCORIA_GENERATOR_BLOCK;
//            }
//
//			default -> {
//				return null;
//			}
//        }
//	}
//
//	public static boolean isEnabled(BlockEntry<CobblestoneGeneratorBlock> block){
//        if (block.equals(COBBLESTONE_GENERATOR_BLOCK)) {
//            return Config.common().cobblestoneGeneratorEnabled.get();
//        } else if (block.equals(STONE_GENERATOR_BLOCK)) {
//            return Config.common().stoneGeneratorEnabled.get();
//        } else if (block.equals(BASALT_GENERATOR_BLOCK)) {
//            return Config.common().basaltGeneratorEnabled.get();
//        } else if (block.equals(LIMESTONE_GENERATOR_BLOCK)) {
//            return Config.common().limestoneGeneratorEnabled.get();
//        } else if (block.equals(SCORIA_GENERATOR_BLOCK)) {
//            return Config.common().scoriaGeneratorEnabled.get();
//        }
//        return false;
//    }

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering blocks for " + CreateCobblestoneMod.NAME);

        if (Config.common().cobblestoneGeneratorEnabled.get()) {
            COBBLESTONE_GENERATOR_BLOCK = REGISTRATE.block("cobblestone_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.COBBLESTONE))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().stoneGeneratorEnabled.get()) {
            STONE_GENERATOR_BLOCK = REGISTRATE.block("stone_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.STONE))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().basaltGeneratorEnabled.get()) {
            BASALT_GENERATOR_BLOCK = REGISTRATE.block("basalt_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.BASALT))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().limestoneGeneratorEnabled.get()) {
            LIMESTONE_GENERATOR_BLOCK = REGISTRATE.block("limestone_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.LIMESTONE))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().scoriaGeneratorEnabled.get()) {
            SCORIA_GENERATOR_BLOCK = REGISTRATE.block("scoria_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.SCORIA))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().deepslateGeneratorEnabled.get()) {
            DEEPSLATE_GENERATOR_BLOCK = REGISTRATE.block("deepslate_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.DEEPSLATE))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }

        if (Config.common().cobbledDeepslateGeneratorEnabled.get()) {
            COBBLED_DEEPSLATE_GENERATOR_BLOCK = REGISTRATE.block("cobbled_deepslate_generator", p -> new CobblestoneGeneratorBlock(p, GeneratorType.COBBLED_DEEPSLATE))
                    .properties(p -> p.mapColor(MapColor.COLOR_BROWN))
                    .transform(BlockStressDefaults.setImpact(Config.common().generatorStress.get()))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .item()
                    .properties(p -> p.arch$tab(CreativeTabs.getBaseTabKey()))
                    .transform(customItemModel())
                    .register();
        }
    }
}
