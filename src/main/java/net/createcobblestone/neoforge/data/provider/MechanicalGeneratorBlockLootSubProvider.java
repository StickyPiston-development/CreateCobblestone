package net.createcobblestone.neoforge.data.provider;

import net.createcobblestone.neoforge.index.Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MechanicalGeneratorBlockLootSubProvider extends BlockLootSubProvider {

    MechanicalGeneratorBlockLootSubProvider(HolderLookup.Provider registries) {
        // no explosion-resistant items, enable all feature flags, pass registries
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        var mechanical_generator = Blocks.MECHANICAL_GENERATOR_BLOCK.get();

        this.add(mechanical_generator, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(mechanical_generator)
                                .apply(
                                        // Copy BE -> item component(s)
                                        CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                .include(DataComponents.BLOCK_ENTITY_DATA) // <— key part
                                )
                        )
                        .when(ExplosionCondition.survivesExplosion())
        ));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // Return all blocks you add loot for (expand this as you add more)
        return Set.of(Blocks.MECHANICAL_GENERATOR_BLOCK.get());
    }
}
