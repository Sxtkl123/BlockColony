package com.iicsadog.blocksblocks.core.util;

import static net.minecraft.world.level.block.LeavesBlock.PERSISTENT;

import com.iicsadog.blocksblocks.core.util.info.TreeInfo;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;

public class TreeUtils {

    private static final List<Vec3i> NEIGHBORS = List.of(
        new Vec3i(0, 0, 1), new Vec3i(1, 0, 0),
        new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0),
        new Vec3i(1, 0, 1), new Vec3i(-1, 0, -1),
        new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1),
        new Vec3i(0, 1, 1), new Vec3i(1, 1, 0),
        new Vec3i(0, 1, -1), new Vec3i(-1, 1, 0),
        new Vec3i(1, 1, 1), new Vec3i(-1, 1, -1),
        new Vec3i(1, 1, -1), new Vec3i(-1, 1, 1),
        new Vec3i(0, -1, 1), new Vec3i(1, -1, 0),
        new Vec3i(0, -1, -1), new Vec3i(-1, -1, 0),
        new Vec3i(1, -1, 1), new Vec3i(-1, -1, -1),
        new Vec3i(1, -1, -1), new Vec3i(-1, -1, 1),
        new Vec3i(0, 1, 0), new Vec3i(0, -1, 0)
    );

    private static final int MAX_SIZE = 512;

    /**
     * 从一个方块的位置获取树，如果该方块不是树，则会返回空。
     *
     * @param level 维度信息
     * @param pos 要判断的位置
     * @return 树的信息，如果该位置并非为树，则返回为空。
     * @author sxtkl
     * @since 2025/11/22
     */
    public static Optional<TreeInfo> getTree(ServerLevel level, BlockPos pos) {
        // 如果该位置不为树（绯红/诡异菌柄不视为树），则直接返回空
        if (!level.getBlockState(pos).is(BlockTags.LOGS)) {
            return Optional.empty();
        }
        TreeInfo info = upperGrow(pos, level);
        if (checkLeaves(info, level)) {
            return Optional.of(info);
        }
        return Optional.empty();
    }

    private static TreeInfo upperGrow(BlockPos start, ServerLevel level) {
        Block target = level.getBlockState(start).getBlock();
        Queue<BlockPos> queue = new ArrayDeque<>(List.of(start));
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> trunk = new HashSet<>();
        Set<BlockPos> root = new HashSet<>();
        int minY = start.getY();
        while (!queue.isEmpty()) {
            BlockPos p = queue.poll();
            trunk.add(p);
            visited.add(p);
            if (p.getY() < minY) {
                minY = p.getY();
            }
            for (Vec3i neighbour : NEIGHBORS) {
                BlockPos neighbourPos = p.offset(neighbour);
                if (!level.getBlockState(neighbourPos).is(target)) {
                    continue;
                }
                if (visited.contains(neighbourPos)) {
                    continue;
                }
                queue.add(neighbourPos);
            }
            if (trunk.size() >= MAX_SIZE) {
                break;
            }
        }
        for (BlockPos log : trunk) {
            if (log.getY() == minY) {
                root.add(log);
            }
        }
        return new TreeInfo(root, trunk, target);
    }

    private static boolean checkLeaves(TreeInfo info, ServerLevel level) {
        for (BlockPos log : info.trunk()) {
            for (Vec3i neighbour : NEIGHBORS) {
                BlockPos neighbourPos = log.offset(neighbour);
                if (level.getBlockState(neighbourPos).getBlock() instanceof LeavesBlock
                    && !level.getBlockState(neighbourPos).getValue(PERSISTENT)) {
                    return true;
                }
            }
        }
        return false;
    }

}
