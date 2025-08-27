package net.createcobblestoneneoforge.index;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.createcobblestoneneoforge.CreateCobblestoneNeoForge;
import net.createcobblestoneneoforge.blocks.MechanicalGeneratorBlockEntity;
import net.createcobblestoneneoforge.blocks.MechanicalGeneratorVisual;
import net.createcobblestoneneoforge.blocks.MechanicalGeneratorRenderer;

import static net.createcobblestoneneoforge.CreateCobblestoneNeoForge.REGISTRATE;
import static net.createcobblestoneneoforge.index.Blocks.MECHANICAL_GENERATOR_BLOCK;

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
