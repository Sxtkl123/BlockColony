package com.iicsadog.blocksblocks.api.component;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.components.SoulComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 模组的组件。
 *
 * @author sxtkl
 * @since 2025/10/7
 */
public class ModComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(
        Registries.DATA_COMPONENT_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulComponent>> BLOCKMEN =
        COMPONENTS.registerComponentType(
            "blockmen",
            builder -> builder.persistent(SoulComponent.CODEC).networkSynchronized(SoulComponent.STREAM_CODEC)
    );

}
