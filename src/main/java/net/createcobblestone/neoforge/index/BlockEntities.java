package net.createcobblestone.neoforge.index;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorBlockEntity;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorVisual;
import net.createcobblestone.neoforge.blocks.MechanicalGeneratorRenderer;

import static net.createcobblestone.neoforge.CreateCobblestoneNeoForge.REGISTRATE;
import static net.createcobblestone.neoforge.index.Blocks.MECHANICAL_GENERATOR_BLOCK;

public class BlockEntities {
    public static BlockEntityEntry<MechanicalGeneratorBlockEntity> MECHANICAL_GENERATOR;

    public static void register() {
        CreateCobblestoneNeoForge.LOGGER.info("Registering blockEntities for " + CreateCobblestoneNeoForge.NAME);

        MECHANICAL_GENERATOR = REGISTRATE
                .blockEntity("mechanical_generator", MechanicalGeneratorBlockEntity::new)
                .visual(() -> MechanicalGeneratorVisual::new)
                .validBlocks(MECHANICAL_GENERATOR_BLOCK)
                .renderer(() -> MechanicalGeneratorRenderer::new)
                .register();
    }
}
