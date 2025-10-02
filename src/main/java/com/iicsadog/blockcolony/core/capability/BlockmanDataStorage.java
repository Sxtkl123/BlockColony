package com.iicsadog.blockcolony.core.capability;

import com.iicsadog.blockcolony.api.capability.IBlockmanDataStorage;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.level.block.Block;

/**
 * 方块人数据存储能力。
 *
 * @author sxtkl
 * @since 2025/10/3
 */
public class BlockmanDataStorage implements IBlockmanDataStorage {

    private String name;

    private final Set<Block> rejectedBlocks = new HashSet<>();

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addRejectedBlock(Block block) {
        rejectedBlocks.add(block);
    }

    @Override
    public boolean isRejectedBlock(Block block) {
        return rejectedBlocks.contains(block);
    }
}
