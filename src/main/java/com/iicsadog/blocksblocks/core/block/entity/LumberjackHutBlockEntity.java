package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.entity.BaseTaskHutBlockEntity;
import com.iicsadog.blocksblocks.core.entity.ai.Task;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.core.info.TreeInfo;
import com.iicsadog.blocksblocks.core.worker.TreeRetrieverWorker;
import com.mojang.serialization.Codec;
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
public class LumberjackHutBlockEntity extends BaseTaskHutBlockEntity<TreeInfo> {

    public static final ResourceLocation HUT_TYPE = BlocksBlocks.namespace("lumberjack");

    private static final int SCAN_RATE = 100;

    private int timeToTick = SCAN_RATE;

    private TreeRetrieverWorker worker;

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
        return HUT_TYPE;
    }

    @Override
    protected Codec<Task<TreeInfo>> getTaskCodec() {
        return TreeInfo.TASK_CODEC;
    }

    /**
     * 每个tick会触发的内容。
     *
     * @param level 维度
     * @param pos 方块位置
     * @author sxtkl
     * @since 2025/11/27
     */
    public void tick(ServerLevel level, BlockPos pos) {
        if (--timeToTick > 0L) {
            return;
        }
        timeToTick = SCAN_RATE;
        if (worker == null || !worker.hasWork()) {
            worker = new TreeRetrieverWorker(pos.offset(-8, -1, -8), pos.offset(8, 1, 8), level, this);
            taskMap.forEach((ignore, task) -> worker.getVisited().addAll(task.data().trunk()));
            WorldWorkerManager.addWorker(worker);
        }
    }
}
