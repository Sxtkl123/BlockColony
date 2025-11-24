package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.info.TreeInfo;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES =
        DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<UUID>> BLOCKMAN_HUT_ID =
        MEMORY_TYPES.register("blockman_hut_id", () -> new MemoryModuleType<>(Optional.of(UUIDUtil.CODEC)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<TreeInfo>> TREE_INFO =
        MEMORY_TYPES.register("tree_info", () -> new MemoryModuleType<>(Optional.of(TreeInfo.CODEC)));

}
