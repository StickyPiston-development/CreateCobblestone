package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createcobblestone.index.Config;
import net.createcobblestone.util.GeneratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;

public class CobblestoneGeneratorBlockEntity extends KineticBlockEntity implements Container {

    final NonNullList<ItemStack> items;
    private final int size;
    private double available = 0d;

    public GeneratorType type = GeneratorType.COBBLESTONE;

    public CobblestoneGeneratorBlockEntity(BlockEntityType<? extends CobblestoneGeneratorBlockEntity> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        this.size = 1;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);

        saveType(compound);
        //CreateCobblestoneMod.LOGGER.error("Saving: " + compound.getAsString());
    }

    public void saveType(CompoundTag tag){
        tag.putString("type", type.name());
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);

        //CreateCobblestoneMod.LOGGER.error("Reading: " + compound.getAsString());
        String generatorType = compound.getString("type");

        // FIX: #3 Placing a generator using a schematticannon crashes a server.
        if (generatorType.isEmpty()){
            this.type = GeneratorType.COBBLESTONE;
            return;
        }

        this.type = GeneratorType.valueOf(generatorType);
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

        if (type.getBlock() != null) {
            if (this.available < 64) {
                this.available = this.available + abs(getSpeed() / Config.common().generatorRatio.get());
            }

            int current = this.items.get(0).getCount();
            int added = (int) this.available;
            this.available -= added;

            this.items.set(0, new ItemStack(type.getBlock(), Math.min(current + added, Config.common().maxStorage.get())));
        }
    }

    @Override
    public float calculateStressApplied() {
        float impact = Config.common().generatorStress.get();
        this.lastStressApplied = impact;
        return impact;
    }


}
