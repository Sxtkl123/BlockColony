package com.iicsadog.blocksblocks.core.subscriber.server;

import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * ServerStoppedSubscriber 类是一个事件订阅器，用于监听服务器停止事件。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerStoppedSubscriber {

    /**
     * 在服务器停止时调用此方法，用于清理服务器实例和数据管理器实例。
     * 此方法作为服务器停止事件的处理函数，负责释放相关资源并清理数据。
     *
     * @param evt 服务器停止事件对象，包含服务器停止的相关信息
     * @author sxtkl
     * @since 2025/10/15
     */
    @SubscribeEvent
    public static void clearDataManage(ServerStoppedEvent evt) {
        ColonyDataManager.onServerStop();
    }

}
