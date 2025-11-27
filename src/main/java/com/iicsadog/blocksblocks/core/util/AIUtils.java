package com.iicsadog.blocksblocks.core.util;

import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

/**
 * AI相关工具类。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class AIUtils {

    /**
     * 检查实体的工作状态。
     *
     * @param entity 方块人
     * @param status 想检测的状态
     * @return 是否是想检测的状态
     * @author sxtkl
     * @since 2025/11/27
     */
    public static boolean checkStatus(BlockmanEntity entity, ResourceLocation status) {
        Optional<ResourceLocation> memory = entity.getBrain().getMemory(ModMemoryModuleTypes.STATUS.get());
        return memory.map(resourceLocation -> resourceLocation.equals(status)).orElse(false);
    }

    /**
     * 更新工作状态。
     *
     * @param entity 方块人
     * @param status 更新后的状态
     * @author sxtkl
     * @since 2025/11/27
     */
    public static void updateStatus(BlockmanEntity entity, ResourceLocation status) {
        if (entity.getBrain().checkMemory(ModMemoryModuleTypes.STATUS.get(), MemoryStatus.REGISTERED)) {
            entity.getBrain().setMemory(ModMemoryModuleTypes.STATUS.get(), status);
        }
    }

    /**
     * 清除状态（进入空闲模式）。
     *
     * @param entity 方块人
     * @author sxtkl
     * @since 2025/11/27
     */
    public static void clearStatus(BlockmanEntity entity) {
        if (entity.getBrain().checkMemory(ModMemoryModuleTypes.STATUS.get(), MemoryStatus.REGISTERED)) {
            entity.getBrain().eraseMemory(ModMemoryModuleTypes.STATUS.get());
        }
    }

}
