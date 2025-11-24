package com.iicsadog.blocksblocks.core.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.iicsadog.blocksblocks.core.entity.ai.behavior.SetWalkTargetFromWorkPos;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.schedule.Activity;

public class BlockmanAI {

    protected static Brain<?> makeBrain(BlockmanEntity blockman, Brain<BlockmanEntity> brain) {
        // 核心活动
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink()
        ));

        // 闲置活动 - 当没有其他事情做时的行为
        brain.addActivity(Activity.IDLE, ImmutableList.of(
            // 看向最近的玩家
            Pair.of(0, SetEntityLookTarget.create(EntityType.PLAYER, 4.0f)),
            // 随机游走、去工作方块处或者啥也不干
            Pair.of(1, new RunOne<>(ImmutableList.of(
                Pair.of(SetWalkTargetFromWorkPos.create(1.0f, 1), 1),
                Pair.of(RandomStroll.stroll(0.6f), 2),
                Pair.of(new DoNothing(30, 60), 2)
            )))
        ));

        // 设置默认活动为闲置
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    protected static void updateActivity(BlockmanEntity blockman) {
        blockman.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

}
