package com.iicsadog.blocksblocks.core.util;

import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class AIUtils {

    public static boolean checkStatus(BlockmanEntity entity, ResourceLocation status) {
        Optional<ResourceLocation> memory = entity.getBrain().getMemory(ModMemoryModuleTypes.STATUS.get());
        return memory.map(resourceLocation -> resourceLocation.equals(status)).orElse(false);
    }

    public static void updateStatus(BlockmanEntity entity, ResourceLocation status) {
        if (entity.getBrain().checkMemory(ModMemoryModuleTypes.STATUS.get(), MemoryStatus.REGISTERED)) {
            entity.getBrain().setMemory(ModMemoryModuleTypes.STATUS.get(), status);
        }
    }

    public static void clearStatus(BlockmanEntity entity) {
        if (entity.getBrain().checkMemory(ModMemoryModuleTypes.STATUS.get(), MemoryStatus.REGISTERED)) {
            entity.getBrain().eraseMemory(ModMemoryModuleTypes.STATUS.get());
        }
    }

}
