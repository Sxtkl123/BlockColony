package com.iicsadog.blockcolony.core.subscriber.common;

import com.iicsadog.blockcolony.api.entity.ModEntities;
import com.iicsadog.blockcolony.core.entity.BlockmanEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/**
 * 创建实体属性事件。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
@EventBusSubscriber
public class EntityAttributeCreationSubscriber {

    /**
     * 创建实体属性事件。
     *
     * @param evt 实体属性事件
     * @author sxtkl
     * @since 2025/9/29
     */
    @SubscribeEvent
    public static void setupAttributes(EntityAttributeCreationEvent evt) {
        evt.put(ModEntities.BLOCKMAN.get(), BlockmanEntity.createAttributes().build());
    }

}
