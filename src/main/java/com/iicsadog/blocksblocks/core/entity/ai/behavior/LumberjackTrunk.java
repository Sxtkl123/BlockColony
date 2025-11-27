package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.HUT_ID;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.LUMBERJACK_TASK;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.STATUS;

import com.google.common.collect.ImmutableMap;
import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.core.block.entity.LumberjackHutBlockEntity;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import com.iicsadog.blocksblocks.core.manager.common.HutEntityCacheManager;
import com.iicsadog.blocksblocks.core.util.AIUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LumberjackTrunk extends Behavior<BlockmanEntity> {

    private static final int TICKS_TO_BREAK = 100;

    private int breakTicks;

    private int lastBreakProcess = -1;

    private List<BlockPos> trunk;

    private BlockPos processingPos;

    public LumberjackTrunk() {
        super(ImmutableMap.of(
            STATUS.get(), MemoryStatus.VALUE_PRESENT,
            LUMBERJACK_TASK.get(), MemoryStatus.VALUE_PRESENT,
            HUT_ID.get(), MemoryStatus.VALUE_PRESENT
        ), Integer.MAX_VALUE);
    }

    @Override
    protected void start(ServerLevel level, BlockmanEntity entity, long gameTime) {
        this.breakTicks = 0;
        Optional<LumberjackTask> mem = entity.getBrain().getMemory(LUMBERJACK_TASK.get());
        this.trunk = mem.map(task -> {
            List<BlockPos> res = new ArrayList<>(task.treeInfo().trunk());
            res.sort((pos1, pos2) -> {
                if (pos1.getY() != pos2.getY()) {
                    return pos2.getY() - pos1.getY();
                }
                if (pos1.getX() != pos2.getX()) {
                    return pos2.getX() - pos1.getX();
                }
                return pos2.getZ() - pos1.getZ();
            });
            return res;
        }).orElse(new ArrayList<>());
    }

    @Override
    protected void tick(ServerLevel level, BlockmanEntity owner, long gameTime) {
        if (this.processingPos == null) {
            this.processingPos = this.trunk.stream().findFirst().orElse(null);
            this.lastBreakProcess = -1;
            this.breakTicks = 0;
        }
        if (this.processingPos == null) {
            return;
        }
        if (level.getBlockState(processingPos).is(Blocks.AIR)) {
            this.trunk.remove(this.processingPos);
            this.processingPos = null;
            return;
        }
        this.breakTicks++;
        int i = (int) (this.breakTicks * 10.0f / TICKS_TO_BREAK);
        if (i != this.lastBreakProcess) {
            level.destroyBlockProgress(owner.getId(), this.processingPos, i);
            this.lastBreakProcess = i;
        }

        if (this.breakTicks == TICKS_TO_BREAK) {
            this.trunk.remove(this.processingPos);
            level.destroyBlock(this.processingPos, true, owner);
            this.processingPos = null;
        }
    }

    @Override
    protected boolean canStillUse(ServerLevel level, BlockmanEntity entity, long gameTime) {
        return !this.trunk.isEmpty();
    }

    @Override
    protected void stop(ServerLevel level, BlockmanEntity entity, long gameTime) {
        AIUtils.clearStatus(entity);
        entity.getBrain().eraseMemory(LUMBERJACK_TASK.get());
        Optional<UUID> optionalId = entity.getBrain().getMemory(HUT_ID.get());
        Optional<LumberjackHutBlockEntity> optionalHut = HutEntityCacheManager.getInstance().getEntity(LumberjackHutBlockEntity.class, optionalId);
        if (optionalHut.isEmpty()) {
            return;
        }
        LumberjackHutBlockEntity hut = optionalHut.get();
        Optional<LumberjackTask> taskMem = entity.getBrain().getMemory(LUMBERJACK_TASK.get());
        if (taskMem.isEmpty()) {
            return;
        }
        hut.finishTask(taskMem.get().taskId());
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, BlockmanEntity owner) {
        return AIUtils.checkStatus(owner, ModBlockmanStatus.LUMBERJACK_TRUNK);
    }
}
