package com.iicsadog.blockcolony.api.capability;

import net.minecraft.world.level.block.Block;

/**
 * 方块酱数据存储接口。
 *
 * @author sxtkl
 * @since 2025/10/2
 */
@SuppressWarnings("unused")
public interface IBlockmanDataStorage {
    /**
     * 设置方块酱名称。
     *
     * @param name 设置的名称
     * @author sxtkl
     * @since 2025/10/2
     */
    void setName(String name);

    /**
     * 获取方块酱名称。
     *
     * @return 获取到的名称
     * @author sxtkl
     * @since 2025/10/2
     */
    String getName();

    /**
     * 添加方块人拒绝寄宿的方块。
     *
     * @param block 被拒绝寄宿的方块。
     * @author sxtkl
     * @since 2025/10/2
     */
    void addRejectedBlock(Block block);

    /**
     * 判断一个方块是否是一个被拒绝寄宿的方块。
     *
     * @param block 被拒绝的方块
     * @return 是否被拒绝
     * @author sxtkl
     * @since 2025/10/2
     */
    boolean isRejectedBlock(Block block);
}
