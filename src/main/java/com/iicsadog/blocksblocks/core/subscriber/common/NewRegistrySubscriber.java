package com.iicsadog.blocksblocks.core.subscriber.common;

import com.iicsadog.blocksblocks.api.ModRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

/**
 * 新的可注册类事件订阅器。
 *
 * @author sxtkl
 * @since 2025/12/2
 */
@EventBusSubscriber
public class NewRegistrySubscriber {

    /**
     * 创建新的可注册类。
     *
     * @param evt 事件
     * @author sxtkl
     * @since 2025/12/2
     */
    @SubscribeEvent
    public static void newRegistry(NewRegistryEvent evt) {
        evt.register(ModRegistries.JOB);
    }

}
