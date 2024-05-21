package net.createcobblestone.index;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.blocks.CobblestoneGeneratorBlockEntity;
import net.createcobblestone.blocks.CobblestoneGeneratorInstance;
import net.createcobblestone.blocks.CobblestoneGeneratorRenderer;

import static net.createcobblestone.CreateCobblestoneMod.REGISTRATE;

public class BlockEntities {
    public static BlockEntityEntry<CobblestoneGeneratorBlockEntity> COBBLESTONE_GENERATOR = REGISTRATE
            .blockEntity("cobblestone_generator", CobblestoneGeneratorBlockEntity::new)
            .instance(() -> CobblestoneGeneratorInstance::new)
            .validBlocks(Blocks.COBBLESTONE_GENERATOR_BLOCK_BLOCK_ENTRY)
            .renderer(() -> CobblestoneGeneratorRenderer::new)
            .register();

    public static void init() {
        CreateCobblestoneMod.LOGGER.info("Registering blockEntities for " + CreateCobblestoneMod.NAME);
    }
}
