package com.iicsadog.blocksblocks.core.entity.ai.sensor;

import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.HUT_ID;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.LUMBERJACK_TASK;
import static com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes.STATUS;

import com.google.common.collect.ImmutableSet;
import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.core.block.entity.LumberjackHutBlockEntity;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import com.iicsadog.blocksblocks.core.manager.common.HutEntityCacheManager;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.phys.Vec3;

public class CloseEnoughToTreeSensor extends Sensor<BlockmanEntity> {
    @Override
    protected void doTick(ServerLevel serverLevel, BlockmanEntity blockmanEntity) {
        Optional<ResourceLocation> statusMem = blockmanEntity.getBrain().getMemory(STATUS.get());
        if (statusMem.isEmpty() || !statusMem.get().equals(ModBlockmanStatus.GO_FOR_A_TREE)) {
            return;
        }
        Optional<LumberjackTask> taskMem = blockmanEntity.getBrain().getMemory(LUMBERJACK_TASK.get());
        if (taskMem.isEmpty()) {
            return;
        }
        BlockPos pos = taskMem.get().treeInfo().root().stream().findAny().orElse(null);
        if (pos == null) {
            return;
        }
        Vec3i entityPos = blockmanEntity.blockPosition();
        Vec3i temp = new Vec3i(entityPos.getX(), 0, entityPos.getZ());
        double dist = temp.distSqr(new Vec3i(pos.getX(), 0, pos.getZ()));
        if (dist > 1.44) {
            return;
        }
        blockmanEntity.getBrain().eraseMemory(STATUS.get());
        blockmanEntity.getBrain().eraseMemory(LUMBERJACK_TASK.get());
        Optional<UUID> optionalId = blockmanEntity.getBrain().getMemory(HUT_ID.get());
        Optional<LumberjackHutBlockEntity> optionalHut = HutEntityCacheManager.getInstance().getEntity(LumberjackHutBlockEntity.class, optionalId);
        if (optionalHut.isEmpty()) {
            return;
        }
        LumberjackHutBlockEntity hut = optionalHut.get();
        hut.finishTask(taskMem.get().taskId());
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(LUMBERJACK_TASK.get(), STATUS.get(), HUT_ID.get());
    }
}
