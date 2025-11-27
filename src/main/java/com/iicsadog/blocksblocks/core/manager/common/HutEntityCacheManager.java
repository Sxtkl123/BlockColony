package com.iicsadog.blocksblocks.core.manager.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * 工作小屋实体与ID之间的缓存管理器。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class HutEntityCacheManager {

    private final Map<UUID, BlockEntity> cache = new HashMap<>();

    private static HutEntityCacheManager instance;

    /**
     * 获取示例。
     *
     * @return 示例
     * @author sxtkl
     * @since 2025/11/27
     */
    public static HutEntityCacheManager getInstance() {
        if (instance == null) {
            instance = new HutEntityCacheManager();
        }
        return instance;
    }

    public Map<UUID, BlockEntity> getCache() {
        return cache;
    }

    /**
     * 通过小屋ID，获得类型转换后的方块实体。
     *
     * @param uuid  小屋ID
     * @return 方块实体
     * @author sxtkl
     * @since 2025/11/27
     */
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> Optional<T> getEntity(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return Optional.of((T) cache.get(uuid));
        }
        return Optional.empty();
    }
}
