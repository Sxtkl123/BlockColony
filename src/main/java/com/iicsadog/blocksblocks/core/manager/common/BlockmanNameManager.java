package com.iicsadog.blocksblocks.core.manager.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * {@code BlockmanNameManager} 类用于管理与 Blockman 名称相关的操作，
 * 包括加载 JSON 数据、解析数据以及提供随机名称的功能。
 * 它利用资源重新加载机制来动态更新名称列表。
 *
 * <p>此类实现了单例模式，以确保始终只有一个实例可以被使用。</p>
 *
 * <p>默认情况下，此类会从指定的资源目录加载相关的 JSON 文件，并解析其中的名称数据。
 * 在应用过程中，会检查 JSON 数据的合法性并提取有效的名称列表。
 * 解析完成后，更新名称列表用于后续随机获取。</p>
 *
 * @author sxtkl
 * @since 2025/10/09
 */
public class BlockmanNameManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static BlockmanNameManager instance;
    private List<String> names;

    /**
     * 构造一个 {@code BlockmanNameManager} 实例。
     *
     * @author sxtkl
     * @since 2025/10/09
     */
    public BlockmanNameManager() {
        super(GSON, "blockman_names");
        this.names = new ArrayList<>();
    }

    /**
     * 获取 {@code BlockmanNameManager} 的单例实例。
     *
     * @return {@code BlockmanNameManager} 的唯一实例。如果实例尚未创建，则会初始化一个新实例并返回。
     * @author sxtkl
     * @since 2025/10/09
     */
    public static BlockmanNameManager getInstance() {
        if (instance == null) {
            instance = new BlockmanNameManager();
        }
        return instance;
    }

    /**
     * 返回一个随机的名称。如果名称列表为空，则返回 null。
     *
     * @param random 一个用于生成随机数的 RandomSource 对象。
     * @return 从名称列表中随机选择的名称。如果名称列表为空，则返回 null。
     * @author sxtkl
     * @since 2025/10/09
     */
    @Nullable
    public String getRandomName(RandomSource random) {
        if (names.isEmpty()) {
            return null;
        }
        return names.get(random.nextInt(names.size()));
    }

    @Override
    protected void apply(
        @NotNull Map<ResourceLocation, JsonElement> loadedData,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        List<String> newNames = new ArrayList<>();

        loadedData.forEach((resourceLocation, jsonElement) -> {
            try {
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("names") && jsonObject.get("names").isJsonArray()) {
                        jsonObject.getAsJsonArray("names").forEach(nameElement -> {
                            if (nameElement.isJsonPrimitive() && nameElement.getAsJsonPrimitive().isString()) {
                                newNames.add(nameElement.getAsString());
                            }
                        });
                    }
                }
            } catch (JsonParseException e) {
                LOGGER.warn("无法解析灵魂名字文件 {}: {}", resourceLocation, e.getMessage());
            }
        });

        if (!newNames.isEmpty()) {
            this.names = newNames;
            LOGGER.info("从数据包加载了 {} 个灵魂名字", this.names.size());
        } else {
            LOGGER.warn("未找到有效的灵魂名字数据。");
        }
    }
}
