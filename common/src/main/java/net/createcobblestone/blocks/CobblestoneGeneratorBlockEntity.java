package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CobblestoneGeneratorBlockEntity extends KineticBlockEntity {

    public ItemStackHandler outputInv;
    public CobblestoneGeneratorBlockEntity(BlockEntityType<? extends CobblestoneGeneratorBlockEntity> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
