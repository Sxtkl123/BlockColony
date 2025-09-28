package com.iicsadog.blockcolony.core.subscriber.common;


import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.api.item.ModItems;
import com.iicsadog.blockcolony.core.entity.RisingItemEntity;
import com.iicsadog.blockcolony.core.event.common.ItemEntityDeathEvent;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.slf4j.Logger;

/**
 * 物品实体死亡时间订阅器。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
@EventBusSubscriber(modid = BlockColony.MODID)
public class ItemEntityDeathSubscriber {

    private static final Logger logger = LogUtils.getLogger();

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
        soulItemEntity.setDeltaMovement(0, 0.1, 0);
    }

}
