package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createcobblestone.CreateCobblestoneMod;
import net.createcobblestone.index.BlockEntities;
import net.createcobblestone.index.Blocks;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CobblestoneGeneratorBlock extends HorizontalKineticBlock implements IBE<CobblestoneGeneratorBlockEntity> {

    GeneratorType generatorType;

    public CobblestoneGeneratorBlock(Properties properties, GeneratorType type) {
        super(properties);
        this.generatorType = type;
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
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        Objects.requireNonNull(this.getBlockEntity(worldIn, pos)).type = generatorType;
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

    //    @Override
//    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
//        if (state.getBlock() != newState.getBlock()) {
//            BlockEntity blockEntity = level.getBlockEntity(pos);
//            if (blockEntity instanceof CobblestoneGeneratorBlockEntity) {
//                ItemStack itemStack = new ItemStack(this);
//                CompoundTag tag = new CompoundTag();
//                ((CobblestoneGeneratorBlockEntity) blockEntity).saveType(tag);
//                itemStack.setTag(tag);
//
//                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
//                level.addFreshEntity(itemEntity);
//            }
//            super.onRemove(state, level, pos, newState, isMoving);
//        }
//    }
//
//    @Override
//    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
//        if (stack.hasTag()) {
//            if (stack.getOrCreateTag().contains("type") && stack.getOrCreateTag().contains("speed")) {
//                BlockEntity blockEntity = world.getBlockEntity(pos);
//                if (blockEntity instanceof CobblestoneGeneratorBlockEntity) {
//                    blockEntity.load(stack.getOrCreateTag());
//                }
//            }
//        }
//    }
}
