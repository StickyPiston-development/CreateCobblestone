package net.createcobblestone.neoforge.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MechanicalGeneratorLootTableProvider extends LootTableProvider {

    public MechanicalGeneratorLootTableProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                out,
                Set.of(), // no special tables to validate
                List.of(new SubProviderEntry(MechanicalGeneratorBlockLootSubProvider::new, LootContextParamSets.BLOCK)),
                registries
        );
    }

}
