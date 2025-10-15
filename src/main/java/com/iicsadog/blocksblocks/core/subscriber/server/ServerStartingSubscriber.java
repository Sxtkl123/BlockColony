package com.iicsadog.blocksblocks.core.subscriber.server;

import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

/**
 * ServerStartingSubscriber 类是一个事件订阅器，用于监听服务器启动事件。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
@EventBusSubscriber
public class ServerStartingSubscriber {

    /**
     * 在服务器启动时初始化数据管理器。
     * 此方法在服务器启动事件触发时调用，用于初始化殖民地数据管理器。
     *
     * @param evt 服务器启动事件对象，包含服务器相关信息
     * @author sxtkl
     * @since 2025/10/15
     */
    @SubscribeEvent
    public static void initDataManagers(ServerStartingEvent evt) {
        ColonyDataManager.onServerStart(evt.getServer());
    }

}
