package net.createcobblestone.index;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlockEntity;
import net.createcobblestone.blocks.CobblestoneGeneratorInstance;
import net.createcobblestone.blocks.CobblestoneGeneratorRenderer;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;
import static net.createcobblestone.index.Blocks.*;

public class BlockEntities {
    public static BlockEntityEntry<CobblestoneGeneratorBlockEntity> COBBLESTONE_GENERATOR;

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering blockEntities for " + CreateCobblestoneMod.NAME);
        List<NonNullSupplier<? extends Block>> suppliers = new ArrayList<>();

        if (Config.common().cobblestoneGeneratorEnabled.get()) {
            suppliers.add(COBBLESTONE_GENERATOR_BLOCK);
        }

        if (Config.common().stoneGeneratorEnabled.get()) {
            suppliers.add(STONE_GENERATOR_BLOCK);
        }

        if (Config.common().basaltGeneratorEnabled.get()) {
            suppliers.add(BASALT_GENERATOR_BLOCK);

        }

        if (Config.common().limestoneGeneratorEnabled.get()) {
            suppliers.add(LIMESTONE_GENERATOR_BLOCK);
        }

        if (Config.common().scoriaGeneratorEnabled.get()) {
            suppliers.add(SCORIA_GENERATOR_BLOCK);
        }

        if (Config.common().deepslateGeneratorEnabled.get()) {
            suppliers.add(DEEPSLATE_GENERATOR_BLOCK);
        }

        if (Config.common().cobbledDeepslateGeneratorEnabled.get()) {
            suppliers.add(COBBLED_DEEPSLATE_GENERATOR_BLOCK);
        }

        @SuppressWarnings("unchecked")
        NonNullSupplier<? extends Block>[] suppliersArray = suppliers.toArray(new NonNullSupplier[0]);

        COBBLESTONE_GENERATOR = REGISTRATE
                .blockEntity("cobblestone_generator", CobblestoneGeneratorBlockEntity::new)
                .instance(() -> CobblestoneGeneratorInstance::new)
                .validBlocks(suppliersArray)
                .renderer(() -> CobblestoneGeneratorRenderer::new)
                .register();
    }
}
