package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.core.data.ColonyData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

/**
 * ColonyDataManager 类负责管理殖民地数据的存储、加载和访问。
 * 该类继承自 SavedData，提供了殖民地数据的持久化存储功能。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ColonyDataManager extends SavedData {
    private static final Map<UUID, ColonyData> colonies = new HashMap<>();
    private static final Map<UUID, UUID> playerColonies = new HashMap<>();
    private static ColonyDataManager instance;
    private static MinecraftServer server;

    private ColonyDataManager() {}

    private static ColonyDataManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ColonyDataManager manager = new ColonyDataManager();
        // 加载 colonies 数据
        if (compoundTag.contains("colonies")) {
            CompoundTag coloniesTag = compoundTag.getCompound("colonies");
            for (String key : coloniesTag.getAllKeys()) {
                CompoundTag colonyTag = coloniesTag.getCompound(key);
                ColonyData colony = new ColonyData();
                colony.setId(colonyTag.getUUID("id"));
                colony.setName(colonyTag.getString("name"));
                colonies.put(UUID.fromString(key), colony);
            }
        }

        // 加载 playerColonies 数据
        if (compoundTag.contains("player_colonies")) {
            CompoundTag playerColoniesTag = compoundTag.getCompound("player_colonies");
            for (String key : playerColoniesTag.getAllKeys()) {
                playerColonies.put(UUID.fromString(key), playerColoniesTag.getUUID(key));
            }
        }

        return manager;
    }

    /**
     * 在服务器启动时调用此方法，用于初始化服务器实例。
     *
     * @param minecraftServer 服务器实例对象
     * @author sxtkl
     * @since 2025/10/15
     */
    public static void onServerStart(MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    /**
     * 在服务器停止时调用此方法，用于清理服务器实例和数据管理器实例。
     *
     * @author sxtkl
     * @since 2025/10/15
     */
    public static void onServerStop() {
        server = null;
        instance = null;
    }

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        // 保存 colonies 数据
        CompoundTag coloniesTag = new CompoundTag();
        for (Map.Entry<UUID, ColonyData> entry : colonies.entrySet()) {
            CompoundTag colonyTag = new CompoundTag();
            colonyTag.putUUID("id", entry.getValue().getId());
            colonyTag.putString("name", entry.getValue().getName());
            coloniesTag.put(entry.getKey().toString(), colonyTag);
        }
        compoundTag.put("colonies", coloniesTag);

        // 保存 playerColonies 数据
        CompoundTag playerColoniesTag = new CompoundTag();
        for (Map.Entry<UUID, UUID> entry : playerColonies.entrySet()) {
            playerColoniesTag.putUUID(entry.getKey().toString(), entry.getValue());
        }
        compoundTag.put("player_colonies", playerColoniesTag);

        return compoundTag;
    }

    /**
     * 获取 ColonyDataManager 的单例实例。
     * 如果服务器未初始化，将抛出 RuntimeException。
     * 如果实例尚未创建，将通过数据存储系统创建并加载实例。
     *
     * @return ColonyDataManager 的单例实例
     * @author sxtkl
     * @since 2025/10/15
     */
    public static ColonyDataManager getInstance() {
        if (server == null) {
            throw new RuntimeException("Server has not been initialized!");
        }
        if (instance == null) {
            instance = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(ColonyDataManager::new, ColonyDataManager::load),
                "colony"
            );
        }
        return instance;
    }

    /**
     * 添加一个新的殖民地数据到管理器中。
     * 此方法会将殖民地数据存储在 colonies 映射中，并根据所有者 ID 建立玩家与殖民地的关联。
     * 调用此方法会标记数据管理器为脏数据，以便在下次保存时更新持久化存储。
     *
     * @param colony 要添加的殖民地数据对象，包含殖民地 ID、所有者 ID 和名称等信息
     * @author sxtkl
     * @since 2025/10/15
     */
    public void addColony(ColonyData colony) {
        colonies.put(colony.getId(), colony);
        playerColonies.put(colony.getOwnerId(), colony.getId());
        this.setDirty();
    }

    /**
     * 根据玩家ID获取该玩家的殖民地数据。
     *
     * @param playerId 玩家的唯一标识符
     * @return 如果玩家拥有殖民地，返回对应的ColonyData对象；否则返回null
     * @author sxtkl
     * @since 2025/10/15
     */
    public ColonyData getColony(UUID playerId) {
        UUID colonyId = playerColonies.getOrDefault(playerId, null);
        if (colonyId == null) {
            return null;
        }
        return colonies.getOrDefault(colonyId, null);
    }
}
