package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.manager.common.HutEntityCacheManager;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SetWalkTargetFromWorkPos {

    public static OneShot<LivingEntity> create(float speedModifier, int closeEnoughDist) {
        return create((ignore) -> true, (ignore) -> speedModifier, closeEnoughDist);
    }

    public static OneShot<LivingEntity> create(Predicate<LivingEntity> canSetWalkTarget, Function<LivingEntity, Float> speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create((instance) -> instance.group(
            instance.absent(MemoryModuleType.WALK_TARGET),
            instance.present(ModMemoryModuleTypes.BLOCKMAN_HUT_ID.get())
        ).apply(instance, (walkTarget, uuid) -> (level, entity, l) -> {
            if (!canSetWalkTarget.test(entity)) {
                return false;
            } else {
                Optional<UUID> id = entity.getBrain().getMemory(ModMemoryModuleTypes.BLOCKMAN_HUT_ID.get());
                id.ifPresent(buildingId -> {
                    BlockEntity hut = HutEntityCacheManager.getInstance().getCache().getOrDefault(buildingId, null);
                    if (hut != null) {
                        walkTarget.set(new WalkTarget(hut.getBlockPos(), speedModifier.apply(entity), closeEnoughDist));
                    }
                });
                return true;
            }
        }));

    }

}
