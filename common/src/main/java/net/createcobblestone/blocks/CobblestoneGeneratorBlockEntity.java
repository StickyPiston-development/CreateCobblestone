package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;

public class CobblestoneGeneratorBlockEntity extends KineticBlockEntity implements Container {

    final NonNullList<ItemStack> items;
    private final int size;

    public GeneratorType type;

    public CobblestoneGeneratorBlockEntity(BlockEntityType<? extends CobblestoneGeneratorBlockEntity> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        this.size = 1;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
        this.type = GeneratorType.COBBLESTONE;
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putString("type", type.name());

        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        this.type = GeneratorType.valueOf(compound.getString("type"));

        super.read(compound, clientPacket);
    }

    @Override
    public int getContainerSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, index, count);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        return itemstack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public void setChanged() {
        // Mark the inventory as changed
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void tick(){
        super.tick();

        this.items.set(0, new ItemStack(type.getBlock(), abs((int) getSpeed()/8)));
    }

    @Override
    public float calculateStressApplied() {
        float impact = 8;
        this.lastStressApplied = impact;
        return impact;
    }
}
