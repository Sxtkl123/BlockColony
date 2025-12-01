package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.entity.ai.sensor.BlockmanHutSensor;
import com.iicsadog.blocksblocks.core.job.lumberjack.sensor.CloseEnoughToTreeSensor;
import com.iicsadog.blocksblocks.core.job.lumberjack.sensor.LumberjackTaskSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 模组的传感器。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class ModSensors {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
        DeferredRegister.create(Registries.SENSOR_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<SensorType<?>, SensorType<BlockmanHutSensor>> BLOCKMAN_HUT =
        SENSOR_TYPES.register("blockman_hut", () -> new SensorType<>(BlockmanHutSensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<LumberjackTaskSensor>> LUMBERJACK_TASK =
        SENSOR_TYPES.register("lumberjack_task", () -> new SensorType<>(LumberjackTaskSensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<CloseEnoughToTreeSensor>> CLOSE_ENOUGH_TO_TREE =
        SENSOR_TYPES.register("close_enough_to_tree", () -> new SensorType<>(CloseEnoughToTreeSensor::new));
}
