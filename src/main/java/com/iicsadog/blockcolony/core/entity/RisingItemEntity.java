package com.iicsadog.blockcolony.core.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * 会向上升起的物品，这种物品如果速度不为零，则会缓缓上升，直到速度变为零。
 *
 * @author sxtkl
 * @since 2025/9/28
 */
public class RisingItemEntity extends ItemEntity {
    private static final double RISE_SPEED = 0.15; // 上升初速度
    private static final double GRAVITY = -0.01;   // 向下的加速度（负值）

    /**
     * 声明一个会上升的物品。
     *
     * @param level 维度
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @param stack 物品堆
     * @author sxtkl
     * @since 2025/9/28
     */
    public RisingItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(level, x, y, z, stack);
        this.setNoGravity(true);
        this.setPickUpDelay(40);
        this.setDeltaMovement(0, RISE_SPEED, 0);
    }

    /**
     * 用于注册的构造方法。
     *
     * @param entityEntityType 实体类型
     * @param level 维度
     * @author sxtkl
     * @since 2025/9/28
     */
    public RisingItemEntity(EntityType<? extends RisingItemEntity> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    public void tick() {
        // 保存当前动量
        Vec3 currentMotion = this.getDeltaMovement();
        Vec3 pos = this.position();
        super.tick();
        if (currentMotion.y <= 0) {
            return;
        }
        // 运行tick后读取原本的动量
        this.setPos(pos);
        this.setDeltaMovement(currentMotion);
        this.move(MoverType.SELF,  this.getDeltaMovement());
        this.hasImpulse = true;
        // 上升阶段：应用加速度
        double newVerticalSpeed = currentMotion.y + GRAVITY;
        if (newVerticalSpeed <= 0) {
            this.setDeltaMovement(currentMotion.x, 0, currentMotion.z);
        } else {
            this.setDeltaMovement(currentMotion.x, newVerticalSpeed, currentMotion.z);
        }
    }
}