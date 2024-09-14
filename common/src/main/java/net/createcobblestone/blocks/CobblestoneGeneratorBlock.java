package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createcobblestone.index.BlockEntities;
import net.createcobblestone.index.Config;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        try {
            GeneratorType type = GeneratorType.fromItem(player.getMainHandItem().getItem());

            if (type.getBlock() == null || type == GeneratorType.NONE || !Config.common().isEnabled(type)) {
                return InteractionResult.FAIL;
            }

            CobblestoneGeneratorBlockEntity be = this.getBlockEntity(level, pos);

            if (be != null) {
                be.updateType(type);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }

        } catch (NullPointerException ignored) {
            return InteractionResult.FAIL;
        }
    }

    //    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        try {
//            GeneratorType type = GeneratorType.valueOf(
//                    player.getMainHandItem().getItem().toString().toUpperCase());
//
//            if (generatorType.getBlock() == null || Blocks.of(generatorType) == null || !Blocks.isEnabled(Blocks.of(type))){
//                return InteractionResult.FAIL;
//            }
//
//            this.generatorType = type;
//
//            System.out.printf(generatorType.getBlock().toString());
//
//            level.setBlock(pos, Blocks.of(generatorType).getDefaultState(), 0);
//            return InteractionResult.SUCCESS;
//        } catch (IllegalArgumentException e) {
//            return super.use(state, level, pos, player, hand, hit);
//        }
//    }
}
