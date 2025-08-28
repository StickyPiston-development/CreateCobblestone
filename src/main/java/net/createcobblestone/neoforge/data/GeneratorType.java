package net.createcobblestone.neoforge.data;

import net.createcobblestone.neoforge.index.Config;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.mojang.text2speech.Narrator.LOGGER;
import static net.createcobblestone.neoforge.index.Blocks.MECHANICAL_GENERATOR_BLOCK;

public class GeneratorType {
    private static final Map<String, GeneratorType> ID_TO_TYPE = new HashMap<>();
    private static final Map<ResourceLocation, GeneratorType> BLOCK_TO_TYPE = new HashMap<>();
    private static final Map<String, String> LEGACY_IDS = Map.of(
            "cobblestone",       "createcobblestone:generator_types/cobblestone.json",
            "stone",             "createcobblestone:generator_types/stone.json",
            "basalt",            "createcobblestone:generator_types/basalt.json",
            "limestone",         "createcobblestone:generator_types/limestone.json",
            "scoria",            "createcobblestone:generator_types/scoria.json",
            "deepslate",         "createcobblestone:generator_types/deepslate.json",
            "cobbled_deepslate", "createcobblestone:generator_types/cobbled_deepslate.json"
    );
    private static final Set<String> DEEPSLATE_PATHS = Set.of(
            "createcobblestone:generator_types/deepslate.json",
            "createcobblestone:generator_types/cobbled_deepslate.json"
    );

    private final String id;
    private final ResourceLocation block;
    private final int generatorStress;
    private final float outputPerSecondPerRpm;
    private final int generatorStorage;

    public static final GeneratorType NONE = initializeNewType("none", BuiltInRegistries.BLOCK.getKey(Blocks.AIR), -1, -1, -1);

    private GeneratorType(String id, ResourceLocation block, int generatorStress, float outputPerSecondPerRpm, int generatorStorage) {
        this.id = id;
        this.block = block;

        this.generatorStress = generatorStress;
        this.outputPerSecondPerRpm = outputPerSecondPerRpm;
        this.generatorStorage = generatorStorage;
    }

    public static void init() {
        // clears all generator types and (re)adds the empty type
        ID_TO_TYPE.clear();
        BLOCK_TO_TYPE.clear();

        LOGGER.info("Generator types cleared");
    }

    public static GeneratorType initializeNewType(String id, ResourceLocation block, int generatorStress, float outputPerSecondPerRpm, int generatorStorage){

        Objects.requireNonNull(block, "block key");
        String normId = normalizeId(id);

        GeneratorType existing = BLOCK_TO_TYPE.get(block);
        if (existing != null) {
            LOGGER.error("Error initializing generator, generator type with block {} already exists (existing id: {}, new id: {})",
                    block, existing.getId(), normId);
            return existing;
        }

        GeneratorType type = new GeneratorType(normId, block, generatorStress, outputPerSecondPerRpm, generatorStorage);
        register(type);

        if (Config.common().enableDebugLogging.get()) {
            LOGGER.info("Generator type {} initialized with block {} generatorStress {} outputPerSecondPerRpm {} generatorStorage {}",
                    normId, block, generatorStress, outputPerSecondPerRpm, generatorStorage);
        }
        return type;
    }

    private static void register(GeneratorType type) {
        ID_TO_TYPE.put(type.id, type);
        BLOCK_TO_TYPE.put(type.block, type);
    }

    public String getId() {
        return id;
    }

    public Block getBlock() throws NullPointerException {
        return BuiltInRegistries.BLOCK.get(block);
    }

    public Item getItem() throws NullPointerException {
        return getBlock().asItem();
    }

    public int getGeneratorStress() {
        return (generatorStress == -1) ? Config.common().generatorStress.get() : generatorStress;
    }

    public float getOutputPerSecondPerRpm() {
        return (outputPerSecondPerRpm == -1)
                ? Config.common().outputPerSecondPerRpm.get().floatValue()
                : outputPerSecondPerRpm;
    }

    public int getStorage() {
        return (generatorStorage == -1) ? Config.common().maxStorage.get() : generatorStorage;
    }

    public boolean isLoaded() {
        return ID_TO_TYPE.containsKey(id);
    }

    public static @NotNull GeneratorType fromId(String id) {
        String norm = normalizeId(id);

        GeneratorType type = ID_TO_TYPE.get(norm);
        if (type != null) return type;

        // legacy remap
        String mapped = LEGACY_IDS.get(norm);
        if (mapped != null) {
            type = ID_TO_TYPE.get(mapped);
            if (type != null) return type;

            if (DEEPSLATE_PATHS.contains(mapped)) {
                LOGGER.error("Deepslate generators are now added using a data pack. Please install it from the mod page. (generator: {})", mapped);
            }
        }

        // fallback
        return NONE;
    }

    public static @NotNull GeneratorType fromCompoundTag(CompoundTag tag) {
        return fromId(tag.getString("type"));
    }

    public static @NotNull GeneratorType fromBlock(Block block) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
        return Objects.requireNonNullElse(BLOCK_TO_TYPE.get(key), NONE);
    }

    public static @NotNull GeneratorType fromItem(Item item) {
        Block block = Block.byItem(item);
        if (block != Blocks.AIR) {
            return fromBlock(block);
        }

        ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(item);
        GeneratorType byItemKey = BLOCK_TO_TYPE.get(itemKey);
        return Objects.requireNonNullElse(byItemKey, NONE);
    }

    public static List<GeneratorType> getTypes() {
        return List.copyOf(ID_TO_TYPE.values());
    }

    public void writeToCompoundTag(CompoundTag tag) {
        tag.putString("id", MECHANICAL_GENERATOR_BLOCK.getId().toString());
        tag.putString("type", id);
    }

    public void writeToItemStack(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        writeToCompoundTag(tag);
        stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));
    }

    private static String normalizeId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Generator type ID cannot be null or empty");
        }
        return id.toLowerCase(Locale.ROOT);
    }


}