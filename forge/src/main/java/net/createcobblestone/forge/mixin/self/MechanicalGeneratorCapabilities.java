package net.createcobblestone.forge.mixin.self;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createcobblestone.blocks.MechanicalGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MechanicalGeneratorBlockEntity.class)
public abstract class MechanicalGeneratorCapabilities extends KineticBlockEntity implements ICapabilityProvider, Container {
    public MechanicalGeneratorCapabilities(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique
    LazyOptional<? extends IItemHandler> createcobblestoneinv$handler = LazyOptional.of(() -> new InvWrapper(this));

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER && !remove) {
            // If down return the down handler otherwise return the one for all other sides
            return createcobblestoneinv$handler.cast();
        }

        return super.getCapability(capability, facing);
    }


    @Override
    public void reviveCaps() {
        super.reviveCaps();
        createcobblestoneinv$handler = LazyOptional.of(() -> new InvWrapper(this));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        createcobblestoneinv$handler.invalidate();
    }
}
