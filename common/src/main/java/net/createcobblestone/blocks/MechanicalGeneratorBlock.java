package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createcobblestone.index.BlockEntities;
import net.createcobblestone.index.Config;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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

            MechanicalGeneratorBlockEntity be = this.getBlockEntity(level, pos);

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
//    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        super.setPlacedBy(world, pos, state, placer, stack);
//
//        // Ensure the world isn't null and is server-side
//        MechanicalGeneratorBlockEntity blockEntity = (MechanicalGeneratorBlockEntity) world.getBlockEntity(pos);
//
//        if (blockEntity == null) return;
//
//        if (blockEntity.type != GeneratorType.NONE) return;
//
//        // Make sure it's the correct block entity type
//        CompoundTag tag = stack.getOrCreateTag(); // Get the tag from the item
//        blockEntity.loadFromItemTag(tag); // Apply the tag to the block entity
//    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);

        MechanicalGeneratorBlockEntity blockEntity = (MechanicalGeneratorBlockEntity) level.getBlockEntity(pos);

        System.out.println(1);

        if (blockEntity == null) return stack;

        System.out.println(2);

        if (blockEntity.type == GeneratorType.NONE) return stack;

        System.out.println(3);

        CompoundTag tag = new CompoundTag();
        tag.putString("type", blockEntity.type.name());
        stack.addTagElement("BlockEntityTag", tag);

        return stack;
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
