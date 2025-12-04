package com.iicsadog.blocksblocks.core.manager.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.manager.element.BlueprintElement;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

public class BlueprintManager extends SimplePreparableReloadListener<Map<ResourceLocation, BlueprintElement>> {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
        .create();

    private static BlueprintManager instance;

    private final Map<ResourceLocation, BlueprintElement> blueprints = new HashMap<>();

    @Override
    @NotNull
    protected Map<ResourceLocation, BlueprintElement> prepare(ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        Map<ResourceLocation, BlueprintElement> result = new HashMap<>();
        resourceManager.listResources("blueprints", location -> location.getPath().endsWith("/blueprint.json")).forEach((file, resource) -> {
            try {
                String path = file.getPath();
                String[] parts = path.split("/");
                if (parts.length != 3) {
                    BlocksBlocks.LOGGER.warn("非法的蓝图风格路径: {}", file.getPath());
                    return;
                }
                String style = parts[1];
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(file.getNamespace(), style);
                try (Reader reader = resource.openAsReader()) {
                    BlueprintElement element = GsonHelper.fromJson(GSON, reader, BlueprintElement.class);
                    BlueprintElement oriElement = result.put(location, element);
                    if (oriElement != null) {
                        throw new IllegalStateException("重复的建筑：" + location);
                    }
                }
            } catch (Exception e) {
                BlocksBlocks.LOGGER.error("无法读取建筑蓝图风格包: {}", file, e);
            }
        });
        return result;
    }

    @Override
    protected void apply(
        @NotNull Map<ResourceLocation, BlueprintElement> resourceLocationJsonElementMap,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profilerFiller
    ) {
        this.blueprints.clear();
        this.blueprints.putAll(resourceLocationJsonElementMap);
    }

    public static BlueprintManager getInstance() {
        if (instance == null) {
            instance = new BlueprintManager();
        }
        return instance;
    }

    public Map<ResourceLocation, BlueprintElement> getBlueprints() {
        return this.blueprints;
    }
}
