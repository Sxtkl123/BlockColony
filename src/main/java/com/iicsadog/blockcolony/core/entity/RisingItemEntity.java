package com.iicsadog.blockcolony.core.entity;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RisingItemEntity extends ItemEntity {
    private static final double RISE_SPEED = 0.05; // 上升初速度
    private static final double GRAVITY = -0.01;   // 向下的加速度（负值）
    private double startY;

    public RisingItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(level, x, y, z, stack);
        this.startY = y;
        this.setNoGravity(true); // 禁用原版重力
        this.setPickUpDelay(40); // 设置拾取延迟
        this.setDeltaMovement(0, RISE_SPEED, 0);
    }

    public RisingItemEntity(EntityType<? extends RisingItemEntity> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        // 保存当前运动状态
        Vec3 currentMotion = this.getDeltaMovement();

        if (currentMotion.y <= 0) {
            return;
        }

        // 上升阶段：应用加速度
        double newVerticalSpeed = currentMotion.y + GRAVITY;

        // 如果速度即将变为负值（开始下落），则进入悬停阶段
        if (newVerticalSpeed <= 0) {
            this.setDeltaMovement(currentMotion.x, 0, currentMotion.z);
        } else {
            this.setDeltaMovement(currentMotion.x, newVerticalSpeed, currentMotion.z);
        }

        // 添加粒子效果（可选）
        if (this.level().isClientSide && this.tickCount % 5 == 0) {
            spawnRiseParticles();
        }
    }

    private void spawnRiseParticles() {
        if (this.level() instanceof ClientLevel clientLevel) {
            for (int i = 0; i < 3; i++) {
                double px = this.getX() + (this.random.nextDouble() - 0.5) * 0.5;
                double py = this.getY() + this.random.nextDouble() * 0.2;
                double pz = this.getZ() + (this.random.nextDouble() - 0.5) * 0.5;

                clientLevel.addParticle(ParticleTypes.END_ROD,
                    px, py, pz,
                    0, 0.02, 0);
            }
        }
    }
}