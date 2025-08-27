package net.createcobblestone.neoforge.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createcobblestone.neoforge.data.GeneratorType;
import net.createcobblestone.neoforge.index.BlockEntities;
import net.createcobblestone.neoforge.index.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MechanicalGeneratorBlock extends HorizontalKineticBlock implements IBE<MechanicalGeneratorBlockEntity> {

    public MechanicalGeneratorBlock(Properties properties) {
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
    public Class<MechanicalGeneratorBlockEntity> getBlockEntityClass() {
        return MechanicalGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalGeneratorBlockEntity> getBlockEntityType() {
        return BlockEntities.MECHANICAL_GENERATOR.get() ;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        try {
            GeneratorType type = GeneratorType.fromItem(player.getMainHandItem().getItem());

            if (type.getBlock() == null || type == GeneratorType.NONE || !Config.common().isEnabled(type)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            MechanicalGeneratorBlockEntity be = this.getBlockEntity(level, pos);

            if (be != null) {
                be.updateType(type);
                return ItemInteractionResult.SUCCESS;
            } else {
                return ItemInteractionResult.FAIL;
            }

        } catch (NullPointerException ignored) {
            return ItemInteractionResult.FAIL;
        }
    }



    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);

        MechanicalGeneratorBlockEntity blockEntity = (MechanicalGeneratorBlockEntity) level.getBlockEntity(pos);

        if (blockEntity == null) return stack;

        blockEntity.type.setTypeToItemStack(stack);

        return stack;
    }

}
