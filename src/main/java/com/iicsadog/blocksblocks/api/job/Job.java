package com.iicsadog.blocksblocks.api.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.api.ai.ModSensors;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.behavior.SetWalkTargetFromWorkPos;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

public abstract class Job {

    private List<MemoryModuleType<?>> memories = new ArrayList<>(
        List.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.PATH,
            ModMemoryModuleTypes.HUT_ID.get(),
            ModMemoryModuleTypes.STATUS.get()
        )
    );

    private List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors = new ArrayList<SensorType<? extends Sensor<? super BlockmanEntity>>>(
        List.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            ModSensors.BLOCKMAN_HUT.get()
        )
    );

    private Brain.Provider<BlockmanEntity> provider;

    public Job(List<MemoryModuleType<?>> memories, List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors, boolean useBase) {
        if (useBase) {
            this.memories.addAll(memories);
            this.sensors.addAll(sensors);
        } else {
            this.memories = memories;
            this.sensors = sensors;
        }
        this.provider = Brain.provider(this.memories, this.sensors);
    }

    public Job(List<MemoryModuleType<?>> memories, List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors) {
        this(memories, sensors, true);
    }

    public Brain<?> makeBrain(BlockmanEntity blockman, Brain<BlockmanEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink()
        ));

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

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    public Brain<?> getBrain(BlockmanEntity blockman) {
        Dynamic<?> dynamic = new Dynamic<>(NbtOps.INSTANCE,
            NbtOps.INSTANCE.createMap(ImmutableMap.of(NbtOps.INSTANCE.createString("memories"), NbtOps.INSTANCE.emptyMap())));
        return makeBrain(blockman, provider.makeBrain(dynamic));
    }

    public void updateActivity(BlockmanEntity blockman) {
        blockman.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.WORK, Activity.IDLE));
    }

    public Brain.Provider<BlockmanEntity> getProvider() {
        return provider;
    }
}
