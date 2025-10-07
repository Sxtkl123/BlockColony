package com.iicsadog.blocksblocks.core.subscriber.client;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.entity.ModEntities;
import com.iicsadog.blocksblocks.core.entity.renderer.BlockmanEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * FML客户端启动事件订阅器。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
@EventBusSubscriber(modid = BlocksBlocks.MODID, value = Dist.CLIENT)
public class FMLClientSetupSubscriber {

    /**
     * 注册方块酱渲染器。
     *
     * @param evt 客户端加载事件。
     * @author sxtkl
     * @since 2025/9/29
     */
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent evt) {
        evt.enqueueWork(() -> {
            EntityRenderers.register(ModEntities.BLOCKMAN.get(), BlockmanEntityRenderer::new);
        });
    }

}
