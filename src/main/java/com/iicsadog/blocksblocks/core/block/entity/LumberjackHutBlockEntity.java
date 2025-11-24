package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.core.info.TreeInfo;
import com.iicsadog.blocksblocks.core.worker.TreeRetrieverWorker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.WorldWorkerManager;

/**
 * 伐木工小屋方块实体。
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public class LumberjackHutBlockEntity extends BaseHutBlockEntity {

    private static final int SCAN_RATE = 100;

    private int timeToTick = SCAN_RATE;

    private TreeRetrieverWorker worker;

    private final List<TreeInfo> tasks = new ArrayList<>();

    /**
     * 伐木工小屋方块实体。
     *
     * @author sxtkl
     * @since 2025/11/18
     */
    public LumberjackHutBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LUMBERJACK_HUT_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public ResourceLocation hutType() {
        return BlocksBlocks.namespace("lumberjack");
    }

    public void tick(ServerLevel level, BlockPos pos) {
        if (--timeToTick > 0L) {
            return;
        }
        timeToTick = SCAN_RATE;
        if (worker == null || !worker.hasWork()) {
            worker = new TreeRetrieverWorker(pos.offset(-8, -1, -8), pos.offset(8, 1, 8), level, this);
            for (TreeInfo task : tasks) {
                worker.getVisited().addAll(task.trunk());
            }
            WorldWorkerManager.addWorker(worker);
        }
    }
}
