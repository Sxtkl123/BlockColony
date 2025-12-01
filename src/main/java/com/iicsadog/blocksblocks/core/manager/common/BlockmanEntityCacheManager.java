package com.iicsadog.blocksblocks.core.manager.common;

import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * 方块人实体与ID之间的缓存管理器。
 *
 * @author sxtkl
 * @since 2025/12/1
 */
public class BlockmanEntityCacheManager {

    private final Map<UUID, BlockmanEntity> cache = new HashMap<>();

    private static BlockmanEntityCacheManager instance;

    public static BlockmanEntityCacheManager getInstance() {
        if (instance == null) {
            instance = new BlockmanEntityCacheManager();
        }
        return instance;
    }

    public Map<UUID, BlockmanEntity> getCache() {
        return cache;
    }


    public Optional<BlockmanEntity> getEntity(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return Optional.of(cache.get(uuid));
        }
        return Optional.empty();
    }
}
