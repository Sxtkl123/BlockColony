package com.iicsadog.blocksblocks.core.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromWorkPos {

    public static OneShot<LivingEntity> create(float speedModifier, int closeEnoughDist) {
        return create((ignore) -> true, (ignore) -> speedModifier, closeEnoughDist);
    }

    public static OneShot<LivingEntity> create(Predicate<LivingEntity> canSetWalkTarget, Function<LivingEntity, Float> speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create((instance) -> instance.group(
            instance.absent(MemoryModuleType.WALK_TARGET),
            instance.present(ModMemoryModuleTypes.BLOCKMAN_HUT_POS.get())
        ).apply(instance, (walkTarget, globalPos) -> (level, entity, l) -> {
            if (!canSetWalkTarget.test(entity)) {
                return false;
            } else {
                Optional<GlobalPos> pos = entity.getBrain().getMemory(ModMemoryModuleTypes.BLOCKMAN_HUT_POS.get());
                pos.ifPresent(blockPos -> walkTarget.set(new WalkTarget(blockPos.pos(), speedModifier.apply(entity), closeEnoughDist)));
                return true;
            }
        }));

    }

}
