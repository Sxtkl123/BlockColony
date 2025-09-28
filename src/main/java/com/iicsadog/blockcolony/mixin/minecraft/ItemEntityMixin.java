package com.iicsadog.blockcolony.mixin.minecraft;

import com.iicsadog.blockcolony.core.event.common.ItemEntityDeathEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 注入物品实体类，使得物品实体在死亡时可以发布事件。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements TraceableEntity {

    /**
     * 物品实体死亡注入，该类不应该被实例化。
     *
     * @param entityType 实体类型
     * @param level 维度
     */
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * 注入到hurt方法中，使得物品实体死亡后可以发布{@link ItemEntityDeathEvent}事件。
     *
     * @param source 伤害源
     * @param amount 伤害数值
     * @param cir 回调结果
     * @author sxtkl
     * @since 2025/9/27
     */
    @Inject(
        method = "hurt",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V",
            shift = At.Shift.AFTER
        )
    )
    public void afterHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        NeoForge.EVENT_BUS.post(new ItemEntityDeathEvent((ItemEntity) (Object) this, source));
    }
}
