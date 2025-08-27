package net.createcobblestone.neoforge.blocks;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.InstancerProvider;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class MechanicalGeneratorVisual extends KineticBlockEntityVisual<MechanicalGeneratorBlockEntity> {

    protected final RotatingInstance rotatingModel;

    public MechanicalGeneratorVisual(VisualizationContext context, MechanicalGeneratorBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        rotatingModel = shaft(instancerProvider(), blockState)
                .setup(blockEntity)
                .setPosition(getVisualPosition());
        rotatingModel.setChanged();
    }

    public static RotatingInstance shaft(InstancerProvider instancerProvider, BlockState state) {
        return instancerProvider.instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                .createInstance()
                .rotateToFace(state.getValue(MechanicalGeneratorBlock.HORIZONTAL_FACING));
    }

    @Override
    public void update(float pt) {
        rotatingModel.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
    }
}