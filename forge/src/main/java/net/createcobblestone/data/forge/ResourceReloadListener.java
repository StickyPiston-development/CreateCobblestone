package net.createcobblestone.data.forge;

import net.createcobblestone.data.GeneratorTypeLoader;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static net.createcobblestone.CreateCobblestoneMod.LOGGER;

public class ResourceReloadListener implements PreparableReloadListener {

    @Override
    public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier barrier, ResourceManager resourceManager,
                                                   @NotNull ProfilerFiller preparationsProfiler, @NotNull ProfilerFiller reloadProfiler,
                                                   @NotNull Executor backgroundExecutor, @NotNull Executor gameExecutor) {

        return CompletableFuture.supplyAsync(() -> {
            preparationsProfiler.push("prepare");
            // Data preparation logic
            LOGGER.info("Preparing resources...");
            preparationsProfiler.pop();
            return null; // Placeholder for any preparation result
        }, backgroundExecutor).thenCompose(backgroundResult -> {
            // Wait for the preparation to complete
            return barrier.wait(backgroundResult).thenRun(() -> {
                reloadProfiler.push("apply");
                // Load generator types here
                try {
                    GeneratorTypeLoader.loadGeneratorTypes(resourceManager);
                    LOGGER.info("Generator types loaded successfully.");
                } catch (Exception e) {
                    LOGGER.error("Failed to load generator types.", e);
                }
                reloadProfiler.pop();
            });
        });
    }
}
