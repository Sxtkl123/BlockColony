package com.iicsadog.blocksblocks.core.manager.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.iicsadog.blocksblocks.BlocksBlocks;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

public class BlueprintManager extends SimplePreparableReloadListener<Map<ResourceLocation, JsonElement>> {

    private static final Gson GSON = new Gson();

    @Override
    @NotNull
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        Map<ResourceLocation, JsonElement> result = new HashMap<>();
        resourceManager.listResources("blueprints", location -> location.getPath().endsWith("/blueprint.json")).forEach((file, resource) -> {
            try {
                String path = file.getPath(); // 例如: "blueprints/gothic/blueprint.json"
                String[] parts = path.split("/");
                if (parts.length != 3) {
                    BlocksBlocks.LOGGER.warn("非法的蓝图风格路径: {}", file.getPath());
                    return;
                }
                String style = parts[1];
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(file.getNamespace(), style);
                try (Reader reader = resource.openAsReader()) {
                    JsonElement element = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                    JsonElement oriElement = result.put(location, element);
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
        @NotNull Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profilerFiller
    ) {

    }
}
