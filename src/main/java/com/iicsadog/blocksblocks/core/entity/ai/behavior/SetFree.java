package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;

public class SetFree {

    public static OneShot<BlockmanEntity> create() {
        return BehaviorBuilder.create(instance -> instance.group(
            instance.registered(ModMemoryModuleTypes.STATUS.get())
        ).apply(instance, (status) -> (level, entity, l) -> {
            status.set(ModBlockmanStatus.FREE);
            return true;
        }));
    }

}
