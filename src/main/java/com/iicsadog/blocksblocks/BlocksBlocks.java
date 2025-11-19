package com.iicsadog.blocksblocks;

import com.iicsadog.blocksblocks.api.ai.ModSensors;
import com.iicsadog.blocksblocks.api.block.ModBlocks;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.api.component.ModComponents;
import com.iicsadog.blocksblocks.api.entity.ModEntities;
import com.iicsadog.blocksblocks.api.item.ModCreativeTab;
import com.iicsadog.blocksblocks.api.item.ModItems;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

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

    public static final Logger LOGGER = LogUtils.getLogger();

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
        ModCreativeTab.CREATIVE_MODE_TABS.register(bus);
        ModSensors.SENSOR_TYPES.register(bus);
        ModChannels.onServerInit();
    }

    /**
     * 创建一个带有模组命名空间的资源位置。
     *
     * @param path 资源的路径
     * @return 创建的ResourceLocation实例
     * @author sxtkl
     * @since 2025/10/26
     */
    public static ResourceLocation namespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
