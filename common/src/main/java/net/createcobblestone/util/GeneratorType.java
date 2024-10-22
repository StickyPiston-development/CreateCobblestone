package net.createcobblestone.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorType {
    private static final Map<String, GeneratorType> ID_TO_TYPE = new HashMap<>();
    private static final Map<Block, GeneratorType> BLOCK_TO_TYPE = new HashMap<>();
    private static final Map<Item, GeneratorType> ITEM_TO_TYPE = new HashMap<>();

    private final String id;
    private final Block block;

    public static GeneratorType NONE = new GeneratorType("none", Blocks.AIR);

    public GeneratorType(String id, Block block) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }

        this.id = id;
        this.block = block;

        ID_TO_TYPE.put(id, this);
        BLOCK_TO_TYPE.put(block, this);
        ITEM_TO_TYPE.put(block.asItem(), this);
    }

    public String getId() {
        return id;
    }

    public Block getBlock() {
        return block;
    }

    public Item getItem() {
        return block.asItem();
    }

    public static GeneratorType fromId(String id) {
        return ID_TO_TYPE.get(id);
    }

    public static GeneratorType fromBlock(Block block) {
        return BLOCK_TO_TYPE.get(block);
    }

    public static GeneratorType fromItem(Item item) {
        return ITEM_TO_TYPE.get(item);
    }

    public static List<GeneratorType> getTypes() {
        return new ArrayList<>(ID_TO_TYPE.values());
    }
}