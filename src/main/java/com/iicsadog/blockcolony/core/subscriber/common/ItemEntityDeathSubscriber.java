package com.iicsadog.blockcolony.core.subscriber.common;


import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.api.item.ModItems;
import com.iicsadog.blockcolony.core.entity.RisingItemEntity;
import com.iicsadog.blockcolony.core.event.common.ItemEntityDeathEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * 物品实体死亡时间订阅器。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
@EventBusSubscriber(modid = BlockColony.MODID)
public class ItemEntityDeathSubscriber {

    /**
     * 焚烧灵魂沙，会得到一个冉冉升起的灵魂。
     *
     * @param e 物品实体死亡事件
     * @author sxtkl
     * @since 2025/9/27
     */
    @SubscribeEvent
    public static void burnSoulSand(final ItemEntityDeathEvent e) {
        if (!e.getDamageSource().is(DamageTypeTags.IS_FIRE)) {
            return;
        }
        if (!e.getEntity().getItem().is(Items.SOUL_SAND)) {
            return;
        }
        Level level = e.getEntity().level();
        ItemStack soul = new ItemStack(ModItems.SOUL_ITEM);
        ItemEntity soulItemEntity = new RisingItemEntity(
            level, e.getEntity().getX(), e.getEntity().getY(), e.getEntity().getZ(), soul
        );
        level.addFreshEntity(soulItemEntity);
    }

}
