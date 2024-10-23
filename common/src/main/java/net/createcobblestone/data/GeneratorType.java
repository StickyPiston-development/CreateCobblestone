package net.createcobblestone.data;

import net.createcobblestone.CreateCobblestoneMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GeneratorType {
    private static final Map<String, GeneratorType> ID_TO_TYPE = new HashMap<>();
    private static final Map<ResourceLocation, GeneratorType> BLOCK_TO_TYPE = new HashMap<>();
    private final String id;
    private final ResourceLocation block;

    public static GeneratorType NONE;

    public static void init() {
        // clears all generator types and (re)adds the empty type
        ID_TO_TYPE.clear();
        BLOCK_TO_TYPE.clear();

        NONE = new GeneratorType("none", Blocks.AIR);
    }

    public GeneratorType(String id, Block block) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }

        this.id = id;
        this.block = block.arch$registryName();

        ID_TO_TYPE.put(id.toLowerCase(), this);
        BLOCK_TO_TYPE.put(this.block, this);
    }

    public GeneratorType(String id, ResourceLocation block) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }

        this.id = id;
        this.block = block;

        ID_TO_TYPE.put(id.toLowerCase(), this);
        BLOCK_TO_TYPE.put(block, this);
    }

    public String getId() {
        return id;
    }

    public Block getBlock() throws NullPointerException
    {
        return Objects.requireNonNull(Minecraft.getInstance().level).registryAccess().registryOrThrow(Registries.BLOCK).get(block);
    }

    public Item getItem() throws NullPointerException
    {
        return getBlock().asItem();
    }

    public static @NotNull GeneratorType fromId(String id) {

        GeneratorType type = ensureType(ID_TO_TYPE.get(id.toLowerCase()));

        System.out.println(id);

        if (type == GeneratorType.NONE) {
            switch (id.toLowerCase()) {
                case "cobblestone":
                    id = "createcobblestone:generator_types/cobblestone.json";
                    break;
                case "stone":
                    id = "createcobblestone:generator_types/stone.json";
                    break;
                case "basalt":
                    id = "createcobblestone:generator_types/basalt.json";
                    break;
                case "limestone":
                    id = "createcobblestone:generator_types/limestone.json";
                    break;
                case "scoria":
                    id = "createcobblestone:generator_types/scoria.json";
                    break;

                case "deepslate":
                    id = "createcobblestone:generator_types/deepslate.json";
                    break;
                case "cobbled_deepslate":
                    id = "createcobblestone:generator_types/cobbled_deepslate.json";
                    break;
            }

            type = ensureType(ID_TO_TYPE.get(id));

            if (type == GeneratorType.NONE && id.equals("createcobblestone:generator_types/deepslate.json") || id.equals("createcobblestone:generator_types/cobbled_deepslate.json")) {
                CreateCobblestoneMod.LOGGER.error("Deepslate generators are now added using a data pack. Please install it from the mod page. (generator: {})", id);
            }
        }

        return type;
    }

    public static @NotNull GeneratorType fromBlock(Block block) {
        return ensureType(BLOCK_TO_TYPE.get(block.arch$registryName()));
    }

    public static @NotNull GeneratorType fromItem(Item item) {
        return ensureType(BLOCK_TO_TYPE.get(item.arch$registryName()));
    }

    public static List<GeneratorType> getTypes() {
        return new ArrayList<>(ID_TO_TYPE.values());
    }

    private static @NotNull GeneratorType ensureType(GeneratorType type) {
        // Return NONE if type is null to stop the game from crashing
        return type == null ? NONE : type;
    }
}