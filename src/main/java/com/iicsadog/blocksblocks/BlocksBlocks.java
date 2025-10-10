package com.iicsadog.blocksblocks;

import com.iicsadog.blocksblocks.api.block.ModBlocks;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.api.component.ModComponents;
import com.iicsadog.blocksblocks.api.entity.ModEntities;
import com.iicsadog.blocksblocks.api.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * 模组 block_colony 的主类，整个模组的入口。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
@Mod(BlocksBlocks.MODID)
public class BlocksBlocks {

    /**
     * 模组的modid。
     */
    public static final String MODID = "blocks_blocks";

    /**
     * 模组主类。
     *
     * @param bus 事件管线
     * @author sxt
     * @since 2025/9/27
     */
    public BlocksBlocks(final IEventBus bus) {
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITY_TYPES.register(bus);
        ModComponents.COMPONENTS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
    }
}
