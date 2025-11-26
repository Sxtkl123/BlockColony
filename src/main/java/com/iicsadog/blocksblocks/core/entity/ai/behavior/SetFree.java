package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SetFree {

    public static OneShot<BlockmanEntity> create() {
        return BehaviorBuilder.create(instance -> instance.group(
            instance.registered(ModMemoryModuleTypes.STATUS.get())
        ).apply(instance, (status) -> (level, entity, l) -> {
            Optional<ResourceLocation> optionalStatus = entity.getBrain().getMemory(ModMemoryModuleTypes.STATUS.get());
            if (optionalStatus.isPresent() && optionalStatus.get().equals(ModBlockmanStatus.FREE)) {
                return false;
            }
            status.set(ModBlockmanStatus.FREE);
            return true;
        }));
    }

}
