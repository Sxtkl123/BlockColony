package com.iicsadog.blocksblocks.core.network.vo;

import com.iicsadog.blocksblocks.core.data.BlockmanData;

/**
 * BlockmenVO 是一个不可变的数据类，用于表示方块人的视图对象。
 * 该类通过 record 实现，仅包含名称属性，并提供了一个静态方法用于从 BlockmanData 创建实例。
 * 主要用于在网络传输和UI展示中传递方块人的基本信息。
 *
 * @author sxtkl
 * @since 2025/10/26
 */
public record BlockmenVO(
    String name
) {
    /**
     * 从 BlockmanData 对象创建 BlockmenVO 实例。
     *
     * @param blockmanData 包含方块人数据的 BlockmanData 对象
     * @return 新创建的 BlockmenVO 实例，仅包含名称属性
     * @author sxtkl
     * @since 2025/10/26
     */
    public static BlockmenVO fromBlockmanData(BlockmanData blockmanData) {
        return new BlockmenVO(blockmanData.getName());
    }
}
