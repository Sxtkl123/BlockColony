package com.iicsadog.blockcolony.api.component;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.core.components.Blockmen;
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
        Registries.DATA_COMPONENT_TYPE, BlockColony.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Blockmen>> BLOCKMEN =
        COMPONENTS.registerComponentType(
            "blockmen",
            builder -> builder.persistent(Blockmen.CODEC).networkSynchronized(Blockmen.STREAM_CODEC)
    );

}
