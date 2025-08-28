package net.createcobblestone.neoforge.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createcobblestone.neoforge.CreateCobblestoneNeoForge;
import net.createcobblestone.neoforge.data.GeneratorType;
import net.createcobblestone.neoforge.index.BlockEntities;
import net.createcobblestone.neoforge.index.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class MechanicalGeneratorBlockEntity extends KineticBlockEntity implements Container {

    final NonNullList<ItemStack> items;
    private final int size = 1;
    private double available = 0d;

    public GeneratorType type;

    public MechanicalGeneratorBlockEntity(BlockEntityType<? extends MechanicalGeneratorBlockEntity> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        items = NonNullList.withSize(size, ItemStack.EMPTY);
        type = GeneratorType.NONE;
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);

        type.writeToCompoundTag(compound);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);

        try {
            updateType(GeneratorType.fromCompoundTag(compound));
        } catch (IllegalArgumentException e) {
            CreateCobblestoneNeoForge.LOGGER.error("Invalid generator type \"{}\", setting type to NONE", GeneratorType.fromCompoundTag(compound).getId());
            type = GeneratorType.NONE;
            setChanged();
        }
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

        Block generatorBlock;

        try {
            generatorBlock = type.getBlock();

            if (!Config.common().isEnabled(type)) {
                updateType(GeneratorType.NONE);
                return;
            }

        } catch (NullPointerException e) {
            if (Config.common().enableDebugLogging.get()) {
                CreateCobblestoneNeoForge.LOGGER.error("Tried accessing generator block before world was loaded");
            }
            return;
        }

        if (generatorBlock != Blocks.AIR) {

            if (!type.isLoaded()){
                updateType(GeneratorType.NONE);
                return;
            }

            if (this.available < type.getStorage()) {
                this.available = min(this.available + abs(getSpeed() * type.getOutputPerSecondPerRpm())/20, type.getStorage());
            }

            int current = this.items.getFirst().getCount();
            int added = (int) this.available;
            this.available -= added;

            this.items.set(0, new ItemStack(generatorBlock, Math.min(current + added, type.getStorage())));
        }
    }

    @Override
    public float calculateStressApplied() {
        float impact = type.getGeneratorStress();
        this.lastStressApplied = impact;
        return impact;
    }

    public void updateType(GeneratorType newType) {

        if (newType == null) {
            if (Config.common().enableDebugLogging.get()) {
                CreateCobblestoneNeoForge.LOGGER.error("Attempted to update generator type to null");
            }
            return;
        }

        if (Config.common().enableDebugLogging.get()) {
            CreateCobblestoneNeoForge.LOGGER.info("Trying to update generator type from \"{}\" to \"{}\"", type.getId(), newType.getId());
        }

        if (!Config.common().isEnabled(newType)){
            if (!Config.common().isEnabled(type)) {
                newType = GeneratorType.NONE;
            } else {
                CreateCobblestoneNeoForge.LOGGER.error("Disabled generator type \"{}\", not changing old generator type. ({})", newType.getId(), type.getId());
                return;
            }
        }

        if (Config.common().enableDebugLogging.get()) {
            CreateCobblestoneNeoForge.LOGGER.info("Changing generator type from \"{}\" to \"{}\"", type.getId(), newType.getId());
        }

        this.type = newType;

        // Make sure no items get ghosted to the new generator to avoid generator rate issues
        this.available = 0;
        this.items.clear();

        this.setChanged();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntities.MECHANICAL_GENERATOR.get(),
                (be, side) -> new InvWrapper(be)
        );
    }
}