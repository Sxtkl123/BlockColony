package com.iicsadog.blocksblocks.core.ai.sensor;

import com.google.common.collect.ImmutableSet;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class BlockmanHutPosSensor extends Sensor<BlockmanEntity> {

    @Override
    protected void doTick(ServerLevel serverLevel, BlockmanEntity entity) {
        UUID blockmanId = entity.getBlockmanId();
        if (blockmanId == null) {
            return;
        }
        BlockmanData data = DataManagers.getInstance(BlockmanDataManager::new).query(blockmanId);
        if (data == null || data.getWorkFor() == null) {
            return;
        }
        BuildingData building = DataManagers.getInstance(BuildingDataManager::new).query(data.getWorkFor());
        if (building == null || building.getPos() == null) {
            return;
        }
        Brain<?> brain = entity.getBrain();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(building.getPos(), 1.0f, 0));
    }

    @NotNull
    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.WALK_TARGET);
    }
}
