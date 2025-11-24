package com.iicsadog.blocksblocks.core.entity.ai.sensor;

import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class TreeInfoSensor extends Sensor<BlockmanEntity> {
    @Override
    protected void doTick(ServerLevel level, BlockmanEntity entity) {

    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of();
    }
}
