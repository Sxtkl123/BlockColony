package com.iicsadog.blocksblocks.core.manager.data;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.AbstractDataManager;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
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
 * BlockmanDataManager 类负责管理游戏中所有Blockman的数据。
 * 该类继承自AbstractDataManager，提供了Blockman数据的加载、保存和访问功能。
 * 使用UUID作为键来存储和检索BlockmanData对象，并确保数据与殖民地的关联。
 *
 * @author sxtkl
 * @since 2025/10/20
 */
public class BlockmanDataManager extends AbstractDataManager {
    private static final String BLOCKMEN = "blockmen";
    private Map<UUID, BlockmanData> blockmen = new HashMap<>();

    @Override
    protected AbstractDataManager load(CompoundTag tag, HolderLookup.Provider provider) {
        this.blockmen = BbNbtUtils.loadMapData(BLOCKMEN, tag, BlockmanData::new);
        return this;
    }

    @Override
    protected ResourceLocation getManagerName() {
        return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "blockman");
    }

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        BbNbtUtils.saveMapData(BLOCKMEN, tag, blockmen);
        return tag;
    }

    /**
     * 根据UUID获取BlockmanData对象。
     * 此方法用于从blockmen映射中检索指定UUID对应的BlockmanData。
     * 如果找不到对应的BlockmanData，则返回null。
     *
     * @param uuid 要查找的Blockman的唯一标识符
     * @return 如果找到对应的BlockmanData则返回该对象，否则返回null
     * @author sxtkl
     * @since 2025/10/20
     */
    @Nullable
    public BlockmanData getBlockmanData(UUID uuid) {
        return this.blockmen.getOrDefault(uuid, null);
    }

    /**
     * 将BlockmanData绑定到数据管理器中。
     * 此方法将BlockmanData对象添加到blockmen映射中，并将其与相应的殖民地关联，
     * 同时标记数据管理器为已修改状态。
     *
     * @param data 要绑定的BlockmanData对象，包含Blockman的所有相关信息
     * @author sxtkl
     * @since 2025/10/20
     */
    public void bind(BlockmanData data) {
        this.blockmen.put(data.getId(), data);
        DataManagers.getInstance(ColonyDataManager::new).bindBlockmanToColony(data.getColonyId(), data.getId());
        this.setDirty();
    }
}
