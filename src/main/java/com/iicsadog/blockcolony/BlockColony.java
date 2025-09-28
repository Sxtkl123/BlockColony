package com.iicsadog.blockcolony;

import com.iicsadog.blockcolony.api.entity.ModEntities;
import com.iicsadog.blockcolony.api.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * 模组 block_colony 的主类，整个模组的入口。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
@Mod(BlockColony.MODID)
public class BlockColony {

    /**
     * 模组的modid。
     */
    public static final String MODID = "block_colony";

    /**
     * 模组主类。
     *
     * @param bus 事件管线
     * @author sxt
     * @since 2025/9/27
     */
    public BlockColony(final IEventBus bus) {
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITY_TYPES.register(bus);
    }
}
