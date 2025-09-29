package com.iicsadog.blockcolony.core.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * 方块人实体类。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
public class BlockmanEntity extends PathfinderMob {


    /**
     * 方块人注册用的构造方法。
     *
     * @param entityType 实体类型
     * @param level 维度
     * @author sxtkl
     * @since 2025/9/29
     */
    public BlockmanEntity(EntityType<? extends BlockmanEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * 在世界的一个位置上生成一个方块人。
     *
     * @param entityType 实体类型
     * @param level 维度
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @author sxtkl
     * @since 2025/9/29
     */
    public BlockmanEntity(EntityType<? extends BlockmanEntity> entityType, Level level, double x, double y, double z) {
        this(entityType, level);
        this.setPos(x, y, z);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
    }

    /**
     * 方块酱属性构建器，这里暂时只赋予了生命值。
     *
     * @return 属性构造器
     * @author sxtkl
     * @since 2025/9/29
     */
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, 20);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
}
