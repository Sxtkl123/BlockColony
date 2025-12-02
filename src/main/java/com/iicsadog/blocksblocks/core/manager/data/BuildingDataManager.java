package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.AbstractDataManager;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * BuildingDataManager 类是建筑物数据的管理器，继承自 AbstractDataManager。
 * 负责管理游戏中所有建筑物的数据，包括加载、保存和更新建筑物信息。
 * 使用 UUID 作为键来存储和检索建筑物数据，确保每个建筑物都有唯一标识。
 *
 * <p>该类通过 BbNbtUtils 工具类实现数据的持久化和加载，将建筑物数据存储在 CompoundTag 中。
 * 管理器名称设置为 "building"，用于在数据系统中唯一标识此类管理器。</p>
 *
 * @author sxtkl
 * @since 2025/10/22
 */
public class BuildingDataManager extends AbstractDataManager<BuildingData> {

    private static final String MANAGER_NAME = "building";

    @Override
    protected @NotNull Codec<BuildingData> dataCodec() {
        return BuildingData.CODEC;
    }

    @Override
    protected ResourceLocation getManagerName() {
        return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, MANAGER_NAME);
    }
}
