package net.createcobblestone.util;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum GeneratorType {
    COBBLESTONE("minecraft:block/cobblestone", Blocks.COBBLESTONE),
    BASALT("minecraft:block/basalt", Blocks.BASALT),
    LIMESTONE("create:block/limestone", AllPaletteStoneTypes.LIMESTONE.baseBlock.get());

    private final String texturePath;
    private final Block block;

    GeneratorType(String texturePath, Block block) {
        this.texturePath = texturePath;
        this.block = block;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public Block getBlock(){
        return this.block;
    }
}
