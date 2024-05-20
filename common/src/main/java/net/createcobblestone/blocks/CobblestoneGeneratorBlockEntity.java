package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.impl.transfer.item.InventoryStorageImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CobblestoneGeneratorBlockEntity extends KineticBlockEntity {

    private final CobblestoneGeneratorInventory inventory;

    public CobblestoneGeneratorBlockEntity(BlockEntityType<? extends CobblestoneGeneratorBlockEntity> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.inventory = new CobblestoneGeneratorInventory(1);
        this.inventory.setItem(0, new ItemStack(Items.COBBLESTONE, 10));
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        ContainerHelper.loadAllItems(tag, this.inventory.items);
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        ContainerHelper.saveAllItems(tag, this.inventory.items);
    }

    public Container getInventory() {
        return this.inventory;
    }
}
