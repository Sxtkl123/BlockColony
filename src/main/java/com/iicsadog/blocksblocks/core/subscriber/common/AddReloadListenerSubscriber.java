package com.iicsadog.blocksblocks.core.subscriber.common;

import com.iicsadog.blocksblocks.core.manager.common.BlockmanNameManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

/**
 * 添加重加载监听器事件。
 *
 * @author sxtkl
 * @since 2025/10/9
 */
@EventBusSubscriber
public class AddReloadListenerSubscriber {

    /**
     * 处理 {@code AddReloadListenerEvent} 事件的方法，用于向资源重载机制中添加 {@code BlockmanNameManager} 的监听器。
     * 通过此方法可以确保在资源重新加载时更新 Blockman 名称数据。
     *
     * @param evt {@code AddReloadListenerEvent} 事件对象，表示资源重载监听器的添加事件。
     *            可以通过该对象向资源加载器中添加自定义的资源监听处理逻辑。
     *
     * @author sxtkl
     * @since 2025/10/9
     */
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent evt) {
        evt.addListener(BlockmanNameManager.getInstance());
    }


}
