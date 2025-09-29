package com.iicsadog.blockcolony.core.subscriber.client;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.core.entity.model.BlockmanEntityModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * 实体渲染器注册事件。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
@EventBusSubscriber(modid = BlockColony.MODID, value = Dist.CLIENT)
public class EntityRenderersSubscriber {

    /**
     * 注册实体渲染器。
     *
     * @param evt 实体渲染注册层事件
     * @author sxtkl
     * @since 2025/9/29
     */
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(BlockmanEntityModel.LAYER_LOCATION, BlockmanEntityModel::createBodyLayer);
    }
}
