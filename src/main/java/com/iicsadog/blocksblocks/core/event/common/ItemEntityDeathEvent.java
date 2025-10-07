package com.iicsadog.blocksblocks.core.event.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.entity.item.ItemEvent;

/**
 * 物品实体死亡事件。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
public class ItemEntityDeathEvent extends ItemEvent {

    private final DamageSource damageSource;

    /**
     * 物品实体死亡事件。
     *
     * @param itemEntity 物品实体
     * @param damageSource 伤害源
     */
    public ItemEntityDeathEvent(ItemEntity itemEntity, DamageSource damageSource) {
        super(itemEntity);
        this.damageSource = damageSource;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }
}
