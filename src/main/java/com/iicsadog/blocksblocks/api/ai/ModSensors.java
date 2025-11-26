package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.entity.ai.sensor.BlockmanHutSensor;
import com.iicsadog.blocksblocks.core.entity.ai.sensor.LumberjackTaskSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSensors {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
        DeferredRegister.create(Registries.SENSOR_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<SensorType<?>, SensorType<BlockmanHutSensor>> BLOCKMAN_HUT =
        SENSOR_TYPES.register("blockman_hut", () -> new SensorType<>(BlockmanHutSensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<LumberjackTaskSensor>> LUMBERJACK_TASK =
        SENSOR_TYPES.register("lumberjack_hut", () -> new SensorType<>(LumberjackTaskSensor::new));
}
