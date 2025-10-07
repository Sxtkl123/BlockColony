package com.iicsadog.blocksblocks.core.subscriber.common;


import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.component.ModComponents;
import com.iicsadog.blocksblocks.api.item.ModItems;
import com.iicsadog.blocksblocks.core.components.Blockmen;
import com.iicsadog.blocksblocks.core.entity.RisingItemEntity;
import com.iicsadog.blocksblocks.core.event.common.ItemEntityDeathEvent;
import java.util.UUID;
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
@EventBusSubscriber(modid = BlocksBlocks.MODID)
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
        soul.set(ModComponents.BLOCKMEN, Blockmen.empty(UUID.randomUUID()));
        ItemEntity soulItemEntity = new RisingItemEntity(
            level, e.getEntity().getX(), e.getEntity().getY(), e.getEntity().getZ(), soul
        );
        level.addFreshEntity(soulItemEntity);
    }

}
