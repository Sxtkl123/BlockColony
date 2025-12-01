package com.iicsadog.blocksblocks.core.job.lumberjack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.api.ai.ModSensors;
import com.iicsadog.blocksblocks.api.job.Job;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.job.lumberjack.behavior.LumberjackTrunk;
import com.iicsadog.blocksblocks.core.job.lumberjack.behavior.SetWalkTargetFromLumberjackTask;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;


// TODO)) 切换工作、方块人死亡等情况下，应该发送一次任务失败通知。
public class LumberjackJob extends Job {
    public LumberjackJob() {
        super(ImmutableList.of(
            ModMemoryModuleTypes.LUMBERJACK_TASK.get()
        ), ImmutableList.of(
            ModSensors.LUMBERJACK_TASK.get(),
            ModSensors.CLOSE_ENOUGH_TO_TREE.get()
        ));
    }

    @Override
    public Brain<?> makeBrain(BlockmanEntity blockman, Brain<BlockmanEntity> brain) {
        Brain<?> result = super.makeBrain(blockman, brain);

        brain.addActivityWithConditions(Activity.WORK, ImmutableList.of(
            Pair.of(0, SetWalkTargetFromLumberjackTask.create(1.5f, 1)),
            Pair.of(1, new LumberjackTrunk())
        ), ImmutableSet.of(
            Pair.of(ModMemoryModuleTypes.STATUS.get(), MemoryStatus.VALUE_PRESENT)
        ));

        return result;
    }
}
