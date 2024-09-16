package net.createcobblestone.index;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.MechanicalGeneratorBlockEntity;
import net.createcobblestone.blocks.MechanicalGeneratorInstance;
import net.createcobblestone.blocks.MechanicalGeneratorRenderer;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;
import static net.createcobblestone.index.Blocks.MECHANICAL_GENERATOR_BLOCK;

public class BlockEntities {
    public static BlockEntityEntry<MechanicalGeneratorBlockEntity> MECHANICAL_GENERATOR;

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering blockEntities for " + CreateCobblestoneMod.NAME);

        MECHANICAL_GENERATOR = REGISTRATE
                .blockEntity("mechanical_generator", MechanicalGeneratorBlockEntity::new)
                .instance(() -> MechanicalGeneratorInstance::new)
                .validBlocks(MECHANICAL_GENERATOR_BLOCK)
                .renderer(() -> MechanicalGeneratorRenderer::new)
                .register();
    }
}
