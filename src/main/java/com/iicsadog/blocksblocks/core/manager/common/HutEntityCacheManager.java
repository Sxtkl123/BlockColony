package com.iicsadog.blocksblocks.core.manager.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.level.block.entity.BlockEntity;

public class HutEntityCacheManager {

    private final Map<UUID, BlockEntity> cache = new HashMap<>();

    private static HutEntityCacheManager instance;

    public static HutEntityCacheManager getInstance() {
        if (instance == null) {
            instance = new HutEntityCacheManager();
        }
        return instance;
    }

    public Map<UUID, BlockEntity> getCache() {
        return cache;
    }

    public Optional<BlockEntity> getEntity(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return Optional.of(cache.get(uuid));
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> Optional<T> getEntity(Class<T> clazz, UUID uuid) {
        if (cache.containsKey(uuid)) {
            return Optional.of((T) cache.get(uuid));
        }
        return Optional.empty();
    }
}
