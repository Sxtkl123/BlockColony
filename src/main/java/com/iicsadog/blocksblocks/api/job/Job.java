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

/**
 * 抽象工作类。
 *
 * @author sxtkl
 * @since 2025/12/2
 */
public abstract class Job {

    private static final List<MemoryModuleType<?>> BASE_MEMORIES = new ArrayList<>(
        List.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.PATH,
            ModMemoryModuleTypes.HUT_ID.get(),
            ModMemoryModuleTypes.STATUS.get()
        )
    );

    @SuppressWarnings("Convert2Diamond")
    private static final List<SensorType<? extends Sensor<? super BlockmanEntity>>> BASE_SENSORS =
        new ArrayList<SensorType<? extends Sensor<? super BlockmanEntity>>>(
        List.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            ModSensors.BLOCKMAN_HUT.get()
        )
    );

    private final List<MemoryModuleType<?>> memories = new ArrayList<>();

    private final List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors = new  ArrayList<>();

    private final Brain.Provider<BlockmanEntity> provider;

    /**
     * 创建一个工作，给出感知器和记忆。
     *
     * @param memories 记忆
     * @param sensors 感知器
     * @param useBase 是否额外引用基础的记忆和感知器
     * @author sxtkl
     * @since 2025/12/2
     */
    public Job(List<MemoryModuleType<?>> memories, List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors, boolean useBase) {
        if (useBase) {
            this.memories.addAll(BASE_MEMORIES);
            this.sensors.addAll(BASE_SENSORS);
        }
        this.memories.addAll(memories);
        this.sensors.addAll(sensors);

        this.provider = Brain.provider(this.memories, this.sensors);
    }

    /**
     * 创建一个工作，使用基础的记忆和感知器。
     *
     * @param memories 记忆
     * @param sensors 感知器
     * @author sxtkl
     * @since 2025/12/2
     */
    public Job(List<MemoryModuleType<?>> memories, List<SensorType<? extends Sensor<? super BlockmanEntity>>> sensors) {
        this(memories, sensors, true);
    }

    /**
     * 为大脑注册一些基本的信息。
     *
     * @param blockman 方块人
     * @param brain 原大脑
     * @return 注册后的大脑
     * @author sxtkl
     * @since 2025/12/2
     */
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

    /**
     * 生成一个新的AI大脑。
     *
     * @param blockman 方块人
     * @return 新的大脑
     * @author sxtkl
     * @since 2025/12/2
     */
    public Brain<?> getBrain(BlockmanEntity blockman) {
        Dynamic<?> dynamic = new Dynamic<>(NbtOps.INSTANCE,
            NbtOps.INSTANCE.createMap(ImmutableMap.of(NbtOps.INSTANCE.createString("memories"), NbtOps.INSTANCE.emptyMap())));
        return makeBrain(blockman, provider.makeBrain(dynamic));
    }

    /**
     * 活动更新，默认按照工作->闲置的状态更新。
     *
     * @param blockman 方块人
     * @author sxtkl
     * @since 2025/12/2
     */
    public void updateActivity(BlockmanEntity blockman) {
        blockman.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.WORK, Activity.IDLE));
    }

    public Brain.Provider<BlockmanEntity> getProvider() {
        return provider;
    }

    public List<MemoryModuleType<?>> getMemories() {
        return memories;
    }

    public List<SensorType<? extends Sensor<? super BlockmanEntity>>> getSensors() {
        return sensors;
    }
}
