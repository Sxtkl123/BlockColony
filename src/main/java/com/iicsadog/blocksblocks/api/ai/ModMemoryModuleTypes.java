package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.entity.ai.Task;
import com.iicsadog.blocksblocks.core.info.TreeInfo;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 模组的记忆类型。
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES =
        DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<UUID>> HUT_ID =
        MEMORY_TYPES.register("hut_id", () -> new MemoryModuleType<>(Optional.of(UUIDUtil.CODEC)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Task<TreeInfo>>> LUMBERJACK_TASK =
        MEMORY_TYPES.register("lumberjack_task", () -> new MemoryModuleType<>(Optional.of(Task.createCodec(TreeInfo.CODEC))));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<ResourceLocation>> STATUS =
        MEMORY_TYPES.register("status", () -> new MemoryModuleType<>(Optional.of(ResourceLocation.CODEC)));

}
