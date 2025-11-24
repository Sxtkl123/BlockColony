package com.iicsadog.blocksblocks.core.util.info;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public record TreeInfo(
    Set<BlockPos> root,
    Set<BlockPos> trunk,
    Block log
) {}
