package net.createcobblestone.data.forge;

import net.createcobblestone.data.GeneratorTypeLoader;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ResourceReloadListener implements PreparableReloadListener {

    @Override
    public @NotNull CompletableFuture<Void> reload(PreparableReloadListener.@NotNull PreparationBarrier barrier, ResourceManager resourceManager,
                                                   @NotNull ProfilerFiller preparationsProfiler, @NotNull ProfilerFiller reloadProfiler,
                                                   @NotNull Executor backgroundExecutor, @NotNull Executor gameExecutor) {

        GeneratorTypeLoader.loadGeneratorTypes(resourceManager);
        return CompletableFuture.completedFuture(null);
    }
}
