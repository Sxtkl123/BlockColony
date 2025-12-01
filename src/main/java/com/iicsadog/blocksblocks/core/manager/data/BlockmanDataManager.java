package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.AbstractDataManager;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * BlockmanDataManager 类负责管理游戏中所有Blockman的数据。
 * 该类继承自AbstractDataManager，提供了Blockman数据的加载、保存和访问功能。
 * 使用UUID作为键来存储和检索BlockmanData对象，并确保数据与殖民地的关联。
 *
 * @author sxtkl
 * @since 2025/10/20
 */
public class BlockmanDataManager extends AbstractDataManager<BlockmanData> {
    private static final String MANAGER_NAME = "blockman";

    @Override
    protected @NotNull Codec<BlockmanData> dataCodec() {
        return BlockmanData.CODEC;
    }

    @Override
    protected ResourceLocation getManagerName() {
        return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, MANAGER_NAME);
    }

    /**
     * 根据殖民地ID获取该殖民地所有绑定的Blockman的列表。
     * 此方法用于从殖民地数据管理器中检索指定殖民地下的所有Blockman的唯一标识符。
     * 如果找不到对应的殖民地或该殖民地没有绑定任何Blockman，则返回一个空列表。
     *
     * @param colonyId 殖民地的唯一标识符，用于标识要查询的殖民地
     * @return 包含该殖民地所有Blockman的列表，如果没有则返回空列表
     * @author sxtkl
     * @since 2025/10/20
     */
    public List<BlockmanData> getColonyBlockmen(UUID colonyId) {
        return this.data.values().stream()
            .filter(blockmanData -> colonyId.equals(blockmanData.getColonyId()))
            .toList();
    }
}
