package com.iicsadog.blocksblocks.core.subscriber.client;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.entity.ModEntities;
import com.iicsadog.blocksblocks.api.network.ModChannels;
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
    public static void registerEntityRender(FMLClientSetupEvent evt) {
        evt.enqueueWork(() -> {
            EntityRenderers.register(ModEntities.BLOCKMAN.get(), BlockmanEntityRenderer::new);
        });
    }

    /**
     * 注册网络通道的客户端初始化方法。
     * 此方法在客户端启动时被调用，用于注册客户端接收的网络数据包处理器。
     *
     * @param evt FML客户端设置事件，包含客户端初始化的相关信息
     * @author sxtkl
     * @since 2025/10/15
     */
    @SubscribeEvent
    public static void registerNetwork(FMLClientSetupEvent evt) {
        ModChannels.onClientInit();
    }
}
