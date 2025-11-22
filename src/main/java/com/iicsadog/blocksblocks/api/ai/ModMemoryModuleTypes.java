package com.iicsadog.blocksblocks.api.ai;

import com.iicsadog.blocksblocks.BlocksBlocks;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES =
        DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<GlobalPos>> BLOCKMAN_HUT_POS =
        MEMORY_TYPES.register("blockman_hut_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));

}
