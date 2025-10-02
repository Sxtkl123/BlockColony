package com.iicsadog.blockcolony.core.subscriber.common;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.api.capability.ModCapabilities;
import com.iicsadog.blockcolony.api.item.ModItems;
import com.iicsadog.blockcolony.core.capability.BlockmanDataStorage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * 能力注册事件订阅器。
 *
 * @author sxtkl
 * @since 2025/10/3
 */
@EventBusSubscriber(modid = BlockColony.MODID)
public class RegisterCapabilitiesSubscriber {

    /**
     * 注册物品能力。
     *
     * @param evt 能力注册事件
     * @author sxtkl
     * @since 2025/10/3
     */
    @SubscribeEvent
    public static void registerItemCapabilities(RegisterCapabilitiesEvent evt) {
        evt.registerItem(
            ModCapabilities.BLOCKMAN_DATA_STORAGE_CAPABILITY,
            (i, v) -> new BlockmanDataStorage(),
            ModItems.SOUL_ITEM.get()
        );
    }

}
