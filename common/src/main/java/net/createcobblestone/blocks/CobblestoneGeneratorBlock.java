package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class CobblestoneGeneratorBlock extends HorizontalKineticBlock implements IBE<CobblestoneGeneratorBlockEntity> {
    public CobblestoneGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferredSide = getPreferredHorizontalFacing(context);
        if (preferredSide != null)
            return defaultBlockState().setValue(HORIZONTAL_FACING, preferredSide);
        return super.getStateForPlacement(context);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public Class<CobblestoneGeneratorBlockEntity> getBlockEntityClass() {
        return CobblestoneGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CobblestoneGeneratorBlockEntity> getBlockEntityType() {
        return null;
    }

    @Override
    public boolean hideStressImpact() {
        return false;
    }
}
