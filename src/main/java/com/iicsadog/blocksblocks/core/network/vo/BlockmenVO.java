package com.iicsadog.blocksblocks.core.network.vo;

import com.iicsadog.blocksblocks.core.data.BlockmanData;

public record BlockmenVO(
    String name
) {
    public static BlockmenVO fromBlockmanData(BlockmanData blockmanData) {
        return new BlockmenVO(blockmanData.getName());
    }
}
