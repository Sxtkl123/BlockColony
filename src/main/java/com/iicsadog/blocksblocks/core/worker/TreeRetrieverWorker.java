package com.iicsadog.blocksblocks.core.worker;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.util.TreeUtils;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.WorldWorkerManager;

public class TreeRetrieverWorker implements WorldWorkerManager.IWorker {

    private final BlockPos startPos;

    private final BlockPos endPos;

    private final ServerLevel world;

    private final Set<BlockPos> visited = new HashSet<>();

    private final int maxIndex;

    private final int offsetX;

    private final int offsetY;

    private final int offsetZ;

    private boolean working = true;

    private int index = 0;

    public TreeRetrieverWorker(BlockPos startPos, BlockPos endPos, ServerLevel world) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.world = world;
        this.offsetX = Math.abs(this.startPos.getX() - this.endPos.getX());
        this.offsetY = Math.abs(this.startPos.getY() - this.endPos.getY());
        this.offsetZ = Math.abs(this.startPos.getZ() - this.endPos.getZ());
        this.maxIndex = calMaxIndex();
    }

    @Override
    public boolean hasWork() {
        return working;
    }

    @Override
    public boolean doWork() {
        if (index >= maxIndex) {
            working = false;
            return false;
        }
        BlockPos currentPos = getCurrentPos();
        index++;
        if (visited.contains(currentPos)) {
            return working;
        }
        // 统计所遍历位置是否含有树木
        TreeUtils.getTree(world, currentPos).ifPresent(info -> {
            visited.addAll(info.trunk());
            BlocksBlocks.LOGGER.info("找到了一颗{}树", info.log());
        });
        return working;
    }

    private int calMaxIndex() {
        return offsetX * offsetY * offsetZ;
    }

    private BlockPos getCurrentPos() {
        int temp = index;
        int x = temp % offsetX;
        temp /= offsetX;
        int z = temp % offsetZ;
        temp /= offsetZ;
        int y = temp % offsetY;
        return startPos.offset(x, y, z);
    }
}
