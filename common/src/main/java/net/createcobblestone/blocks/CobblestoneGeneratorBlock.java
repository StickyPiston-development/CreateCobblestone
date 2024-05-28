package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createcobblestone.index.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
        return BlockEntities.COBBLESTONE_GENERATOR.get() ;
    }

    @Override
    public boolean hideStressImpact() {
        return false;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CobblestoneGeneratorBlockEntity) {
                ItemStack itemStack = new ItemStack(this);
                CompoundTag tag = new CompoundTag();
                ((CobblestoneGeneratorBlockEntity) blockEntity).saveType(tag);
                itemStack.setTag(tag);

                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                level.addFreshEntity(itemEntity);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getOrCreateTag().contains("type") && stack.getOrCreateTag().contains("speed")) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof CobblestoneGeneratorBlockEntity) {
                    blockEntity.load(stack.getOrCreateTag());
                }
            }
        }
    }
}
