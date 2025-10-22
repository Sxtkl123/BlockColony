package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.AbstractDataManager;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.util.BbNbtUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ColonyDataManager 类负责管理殖民地数据的存储、加载和访问。
 * 该类继承自 SavedData，提供了殖民地数据的持久化存储功能。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ColonyDataManager extends AbstractDataManager {
    private static final String COLONIES = "colonies";
    private static final String MANAGER_NAME = "colony";

    private Map<UUID, ColonyData> colonies = new HashMap<>();

    protected ResourceLocation getManagerName() {
        return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, MANAGER_NAME);
    }

    @Override
    protected AbstractDataManager load(CompoundTag tag, HolderLookup.Provider provider) {
        this.colonies = BbNbtUtils.loadMapData(COLONIES, tag, ColonyData::new);
        return this;
    }

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull HolderLookup.Provider provider) {
        BbNbtUtils.saveMapData(COLONIES, compoundTag, colonies);
        return compoundTag;
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
    @Nullable
    public ColonyData getPlayerColony(UUID playerId) {
        return colonies.values().stream()
            .filter(colony -> colony.getOwnerId().equals(playerId))
            .findFirst()
            .orElse(null);
    }

    /**
     * 根据殖民地ID获取对应的ColonyData对象。
     * 此方法用于从殖民地数据管理器中检索指定ID的殖民地数据。
     * 如果找不到对应的殖民地，则返回null。
     *
     * @param colonyId 殖民地的唯一标识符
     * @return 如果找到对应的ColonyData对象则返回该对象，否则返回null
     * @author sxtkl
     * @since 2025/10/20
     */
    @Nullable
    public ColonyData getColony(UUID colonyId) {
        return colonies.getOrDefault(colonyId, null);
    }

}
