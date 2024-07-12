package net.createcobblestone.util;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public enum GeneratorType implements StringRepresentable {
    COBBLESTONE(Blocks.COBBLESTONE),
    STONE(Blocks.STONE),
    BASALT(Blocks.BASALT),
    LIMESTONE(AllPaletteStoneTypes.LIMESTONE.getBaseBlock()),
    SCORIA(AllPaletteStoneTypes.SCORIA.getBaseBlock()),

    DEEPSLATE(Blocks.DEEPSLATE),
    COBBLED_DEEPSLATE(Blocks.COBBLED_DEEPSLATE);

    private final NonNullSupplier<Block> blockNonNullSupplier;
    private final Block block;

    GeneratorType(NonNullSupplier<Block> blockNonNullSupplier) {
        this.block = null;
        this.blockNonNullSupplier = blockNonNullSupplier;
    }

    GeneratorType(Block block) {
        this.block = block;
        this.blockNonNullSupplier = null;
    }

    public Block getBlock(){
        if (this.block != null){
            return this.block;
        }

        if (this.blockNonNullSupplier != null) {
            return this.blockNonNullSupplier.get();
        }

        return null;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name();
    }
}
