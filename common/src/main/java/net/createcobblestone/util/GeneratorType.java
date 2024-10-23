package net.createcobblestone.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

public class GeneratorType {
    private static final Map<String, GeneratorType> ID_TO_TYPE = new HashMap<>();
    private static final Map<ResourceLocation, GeneratorType> BLOCK_TO_TYPE = new HashMap<>();
    private final String id;
    private final ResourceLocation block;

    public static GeneratorType NONE = new GeneratorType("none", Blocks.AIR);

    public GeneratorType(String id, Block block) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }

        this.id = id;
        this.block = null;

        new GeneratorType(id, block.arch$registryName());

    }

    public GeneratorType(String id, ResourceLocation block) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }

        this.id = id;
        this.block = block;

        ID_TO_TYPE.put(id, this);
        BLOCK_TO_TYPE.put(block, this);
    }

    public String getId() {
        return id;
    }

    public Block getBlock() {
        if (Minecraft.getInstance().level == null) {
            return null;
        } else {
            return Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BLOCK).get(block);
        }
    }

    public Item getItem() {
        return getBlock().asItem();
    }

    public static GeneratorType fromId(String id) {
        return ID_TO_TYPE.get(id);
    }

    public static GeneratorType fromBlock(Block block) {
        return BLOCK_TO_TYPE.get(block.arch$registryName());
    }

    public static GeneratorType fromItem(Item item) {
        return BLOCK_TO_TYPE.get(item.arch$registryName());
    }

    public static List<GeneratorType> getTypes() {
        return new ArrayList<>(ID_TO_TYPE.values());
    }
}