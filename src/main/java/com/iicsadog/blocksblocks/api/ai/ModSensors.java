package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.ai.sensor.BlockmanHutPosSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSensors {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
        DeferredRegister.create(Registries.SENSOR_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<SensorType<?>, SensorType<BlockmanHutPosSensor>> BLOCKMAN_HUT_POS =
        SENSOR_TYPES.register("blockman_hut_pos", () -> new SensorType<>(BlockmanHutPosSensor::new));
}
