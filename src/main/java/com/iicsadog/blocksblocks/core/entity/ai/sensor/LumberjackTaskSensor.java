package com.iicsadog.blocksblocks.core.entity.ai.sensor;

import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.HUT_ID;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.LUMBERJACK_TASK;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.STATUS;

import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.core.block.entity.LumberjackHutBlockEntity;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import com.iicsadog.blocksblocks.core.manager.common.HutEntityCacheManager;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class LumberjackTaskSensor extends Sensor<BlockmanEntity> {
    @Override
    protected void doTick(ServerLevel level, BlockmanEntity entity) {
        Optional<ResourceLocation> optStatus = entity.getBrain().getMemory(STATUS.get());
        if (optStatus.isPresent()) {
            return;
        }
        if (entity.getBrain().checkMemory(LUMBERJACK_TASK.get(), MemoryStatus.VALUE_PRESENT)) {
            return;
        }
        Optional<UUID> optBuildingId = entity.getBrain().getMemory(HUT_ID.get());
        if (optBuildingId.isEmpty()) {
            return;
        }
        Optional<LumberjackHutBlockEntity> optionalHut = HutEntityCacheManager.getInstance().getEntity(LumberjackHutBlockEntity.class, optBuildingId.get());
        if (optionalHut.isEmpty()) {
            return;
        }
        LumberjackHutBlockEntity hut = optionalHut.get();
        Optional<LumberjackTask> optionalTask = hut.dequeueTask();
        if (optionalTask.isEmpty()) {
            return;
        }
        LumberjackTask task = optionalTask.get();
        entity.getBrain().setMemory(LUMBERJACK_TASK.get(), task);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of(HUT_ID.get(), LUMBERJACK_TASK.get(), STATUS.get());
    }
}
