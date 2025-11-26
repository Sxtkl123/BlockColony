package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromLumberjackTask {

    public static OneShot<BlockmanEntity> create(float speedModifier, int closeEnoughDist) {
        return create((ignore) -> true, (ignore) -> speedModifier, closeEnoughDist);
    }

    public static OneShot<BlockmanEntity> create(Predicate<BlockmanEntity> canSetWalkTarget, Function<LivingEntity, Float> speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create((instance) -> instance.group(
            instance.absent(MemoryModuleType.WALK_TARGET),
            instance.registered(ModMemoryModuleTypes.STATUS.get()),
            instance.present(ModMemoryModuleTypes.LUMBERJACK_TASK.get())
        ).apply(instance, (walkTarget, status, task) -> (level, entity, l) -> {
            if (!canSetWalkTarget.test(entity)) {
                return false;
            } else {
                status.set(ModBlockmanStatus.GO_FOR_A_TREE);
                Optional<LumberjackTask> optionalTask = entity.getBrain().getMemory(ModMemoryModuleTypes.LUMBERJACK_TASK.get());
                if (optionalTask.isEmpty()) {
                    return false;
                }
                Optional<BlockPos> target = optionalTask.get().treeInfo().root().stream().findAny();
                if (target.isEmpty()) {
                    return false;
                }
                walkTarget.set(new WalkTarget(target.get(), speedModifier.apply(entity), closeEnoughDist));
                return true;
            }
        }));

    }

}
