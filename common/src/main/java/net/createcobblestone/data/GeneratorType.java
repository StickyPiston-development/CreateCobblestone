package net.createcobblestone.data;

import net.createcobblestone.index.Config;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static net.createcobblestone.CreateCobblestoneMod.LOGGER;

public class GeneratorType {
  private static final Map<String, GeneratorType> ID_TO_TYPE = new HashMap<>();
  private static final Map<ResourceLocation, GeneratorType> BLOCK_TO_TYPE = new HashMap<>();

  private final String id;
  private final ResourceLocation block;
  private final int generatorStress;
  private final float outputPerSecondPerRpm;
  private final int generatorStorage;

  public static GeneratorType NONE = initializeNewType("none", Blocks.AIR.arch$registryName(), -1, -1, -1);;

  public static void init() {
    // clears all generator types and (re)adds the empty type
    ID_TO_TYPE.clear();
    BLOCK_TO_TYPE.clear();

    LOGGER.info("Generator types cleared");
  }

  public static GeneratorType initializeNewType(String id, ResourceLocation block, int generatorStress,
      float outputPerSecondPerRpm, int generatorStorage) {

    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Generator type ID cannot be null or empty");
    }

    id = id.toLowerCase();

    if (BLOCK_TO_TYPE.get(block) != null) {
      LOGGER.error(
          "Error initializing generator, generator type with block {} already exists (existing id: {}, new id: {})",
          block, BLOCK_TO_TYPE.get(block).getId(), id);
      return BLOCK_TO_TYPE.get(block);
    }

    GeneratorType type = new GeneratorType(id, block, generatorStress, outputPerSecondPerRpm, generatorStorage);
    ID_TO_TYPE.put(id.toLowerCase(), type);
    BLOCK_TO_TYPE.put(block, type);

    if (Config.common().enableDebugLogging.get()) {
      LOGGER.info("Generator type {} initialized with block {}", id, block);
    }

    return type;
  }

  private GeneratorType(String id, ResourceLocation block, int generatorStress, float outputPerSecondPerRpm,
      int generatorStorage) {
    this.id = id;
    this.block = block;

    this.generatorStress = generatorStress;
    this.outputPerSecondPerRpm = outputPerSecondPerRpm;
    this.generatorStorage = generatorStorage;
  }

  public int getGeneratorStress() {
    if (generatorStress == -1) {
      return Config.common().generatorStress.get();
    }
    return generatorStress;
  }

  public float getOutputPerSecondPerRpm() {
    if (outputPerSecondPerRpm == -1) {
      return Config.common().outputPerSecondPerRpm.get().floatValue();
    }
    return outputPerSecondPerRpm;
  }

  public int getStorage() {
    if (generatorStorage == -1) {
      return Config.common().maxStorage.get();
    }
    return generatorStorage;
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

  public boolean isLoaded() {
    return ID_TO_TYPE.get(id) != null;
  }

  public static @NotNull GeneratorType fromId(String id) {

    GeneratorType type = ensureType(ID_TO_TYPE.get(id.toLowerCase()));

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

      if (type == GeneratorType.NONE && id.equals("createcobblestone:generator_types/deepslate.json")
          || id.equals("createcobblestone:generator_types/cobbled_deepslate.json")) {
        LOGGER.error(
            "Deepslate generators are now added using a data pack. Please install it from the mod page. (generator: {})",
            id);
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
