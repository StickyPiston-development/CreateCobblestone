package net.createcobblestone.blocks;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

import java.util.function.Consumer;

public class MechanicalGeneratorInstance extends KineticBlockEntityVisual<MechanicalGeneratorBlockEntity> {

    protected RotatingInstance shaftModel;

    public MechanicalGeneratorInstance(VisualizationContext context, MechanicalGeneratorBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        final Direction direction = blockState.getValue(MechanicalGeneratorBlock.HORIZONTAL_FACING);
        final Direction.Axis axis = direction.getAxis();

        var instancer = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT));

        this.shaftModel = instancer.createInstance().rotateToFace(Direction.UP, axis);

        shaftModel.setup(blockEntity, axis)
                .setPosition(getVisualPosition())
                .setChanged();
    }

    @Override
    public void update(float v) {
        final Direction direction = blockState.getValue(MechanicalGeneratorBlock.HORIZONTAL_FACING);
        final Direction.Axis axis = direction.getAxis();
        shaftModel.setup(blockEntity, axis, blockEntity.getSpeed()).setChanged();
    }

    @Override
    public void updateLight(float v) {
        this.relight(this.pos, this.shaftModel);
    }

    public void remove(float v) {
        this.shaftModel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer consumer) {
        consumer.accept(this.shaftModel);
    }

    @Override
    protected void _delete() {
        shaftModel.delete();
    }
}
