package com.iicsadog.blockcolony.core.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RisingItemEntity extends ItemEntity {
    private static final double RISE_SPEED = 0.1; // 上升初速度
    private static final double GRAVITY = -0.01;   // 向下的加速度（负值）

    public RisingItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(level, x, y, z, stack);
        this.setNoGravity(true); // 禁用原版重力
        this.setPickUpDelay(40); // 设置拾取延迟
        this.setDeltaMovement(0, RISE_SPEED, 0);
    }

    public RisingItemEntity(EntityType<? extends RisingItemEntity> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    public void tick() {
        // 保存当前动量
        Vec3 currentMotion = this.getDeltaMovement();
        Vec3 pos = this.position();
        super.tick();
        // 运行tick后读取原本的动量
        this.setDeltaMovement(currentMotion);
        this.setPos(pos);
        this.move(MoverType.SELF,  this.getDeltaMovement());

        if (currentMotion.y <= 0) {
            return;
        }

        // 上升阶段：应用加速度
        double newVerticalSpeed = currentMotion.y + GRAVITY;

        if (newVerticalSpeed <= 0) {
            this.setDeltaMovement(currentMotion.x, 0, currentMotion.z);
        } else {
            this.setDeltaMovement(currentMotion.x, newVerticalSpeed, currentMotion.z);
        }
    }
}