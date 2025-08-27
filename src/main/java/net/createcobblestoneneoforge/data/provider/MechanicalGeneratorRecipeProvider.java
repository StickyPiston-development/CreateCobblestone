package net.createcobblestoneneoforge.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import org.jetbrains.annotations.NotNull;

import static com.simibubi.create.AllBlocks.BRASS_CASING;
import static com.simibubi.create.AllBlocks.MECHANICAL_DRILL;
import static com.simibubi.create.AllItems.BRASS_INGOT;
import static com.simibubi.create.AllItems.ELECTRON_TUBE;
import static net.createcobblestoneneoforge.index.Blocks.MECHANICAL_GENERATOR_BLOCK;
import static net.minecraft.world.item.Items.*;

import java.util.concurrent.CompletableFuture;

public class MechanicalGeneratorRecipeProvider extends RecipeProvider {

    public MechanicalGeneratorRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MECHANICAL_GENERATOR_BLOCK.get(),1)
                .pattern("IEI")
                .pattern("WDL")
                .pattern("BBB")
                .define('I', BRASS_INGOT)
                .define('E', ELECTRON_TUBE)
                .define('B', BRASS_CASING)
                .define('W', WATER_BUCKET)
                .define('L', LAVA_BUCKET)
                .define('D', MECHANICAL_DRILL)
                .unlockedBy("has_drill", has(MECHANICAL_DRILL))
                .save(out, MECHANICAL_GENERATOR_BLOCK.getId());
    }
}
