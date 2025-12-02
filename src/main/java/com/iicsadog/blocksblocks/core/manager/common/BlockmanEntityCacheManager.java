package com.iicsadog.blocksblocks.core.manager.common;

import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 方块人实体与ID之间的缓存管理器。
 *
 * @author sxtkl
 * @since 2025/12/1
 */
public class BlockmanEntityCacheManager {

    private final Map<UUID, BlockmanEntity> cache = new HashMap<>();

    private static BlockmanEntityCacheManager instance;

    /**
     * 获取缓存管理器实例。
     *
     * @return 实例
     * @author sxtkl
     * @since 2025/12/2
     */
    public static BlockmanEntityCacheManager getInstance() {
        if (instance == null) {
            instance = new BlockmanEntityCacheManager();
        }
        return instance;
    }

    public Map<UUID, BlockmanEntity> getCache() {
        return cache;
    }

    /**
     * 获取方块人实体。
     *
     * @param uuid 方块人的blockmanId
     * @return 可能为空的方块人生物实体
     * @author sxtkl
     * @since 2025/12/2
     */
    public Optional<BlockmanEntity> getEntity(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return Optional.of(cache.get(uuid));
        }
        return Optional.empty();
    }
}
