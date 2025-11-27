package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
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

/**
 * 设置方块人路径目标为工作位置。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class SetWalkTargetFromWorkPos {

    /**
     * 创建一个走到工作位置的附近的行为。
     *
     * @param speedModifier 速度系数
     * @param closeEnoughDist 走到多近
     * @return 一次性行为实例
     * @author sxtkl
     * @since 2025/11/27
     */
    public static OneShot<BlockmanEntity> create(float speedModifier, int closeEnoughDist) {
        return create((ignore) -> true, (ignore) -> speedModifier, closeEnoughDist);
    }

    /**
     * 创建一个走到工作位置的附近的行为。
     *
     * @param canSetWalkTarget 是否能设置
     * @param speedModifier 速度系数
     * @param closeEnoughDist 走到多近
     * @return 一次性行为实例
     * @author sxtkl
     * @since 2025/11/27
     */
    public static OneShot<BlockmanEntity> create(Predicate<BlockmanEntity> canSetWalkTarget, Function<LivingEntity, Float> speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create((instance) -> instance.group(
            instance.absent(MemoryModuleType.WALK_TARGET),
            instance.present(ModMemoryModuleTypes.HUT_ID.get())
        ).apply(instance, (walkTarget, uuid) -> (level, entity, l) -> {
            if (!canSetWalkTarget.test(entity)) {
                return false;
            } else {
                Optional<UUID> id = entity.getBrain().getMemory(ModMemoryModuleTypes.HUT_ID.get());
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
