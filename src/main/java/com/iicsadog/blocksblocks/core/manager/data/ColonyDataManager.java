package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.AbstractDataManager;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.mojang.serialization.Codec;
import java.util.UUID;
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
public class ColonyDataManager extends AbstractDataManager<ColonyData> {
    private static final String MANAGER_NAME = "colony";

    @Override
    protected @NotNull Codec<ColonyData> dataCodec() {
        return ColonyData.CODEC;
    }

    protected ResourceLocation getManagerName() {
        return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, MANAGER_NAME);
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
        return this.data.values().stream()
            .filter(colony -> colony.getOwnerId().equals(playerId))
            .findFirst()
            .orElse(null);
    }
}
