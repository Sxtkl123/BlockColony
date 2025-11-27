package com.iicsadog.blocksblocks.core.entity.ai.behavior;

import com.iicsadog.blocksblocks.api.ai.ModBlockmanStatus;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

/**
 * 设置方块人路径目标为伐木任务的树。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class SetWalkTargetFromLumberjackTask {

    /**
     * 创建一个走到伐木任务的附近的行为。
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
     * 创建一个走到伐木任务的附近的行为。
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
            instance.present(ModMemoryModuleTypes.STATUS.get()),
            instance.present(ModMemoryModuleTypes.LUMBERJACK_TASK.get())
        ).apply(instance, (walkTarget, status, task) -> (level, entity, l) -> {
            if (!canSetWalkTarget.test(entity)) {
                return false;
            } else {
                Optional<ResourceLocation> optionalStatus = entity.getBrain().getMemory(ModMemoryModuleTypes.STATUS.get());
                if (optionalStatus.isEmpty() || !optionalStatus.get().equals(ModBlockmanStatus.GO_FOR_A_TREE)) {
                    return false;
                }
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
