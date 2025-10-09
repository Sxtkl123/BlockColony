package com.iicsadog.blocksblocks.core.entity;

import com.iicsadog.blocksblocks.api.entity.ModEntities;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 方块人实体类。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
public class BlockmanEntity extends PathfinderMob {

    private static final EntityDataAccessor<BlockState>
        BLOCK_STATE = SynchedEntityData.defineId(BlockmanEntity.class, EntityDataSerializers.BLOCK_STATE);

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
     * @param level 维度
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @author sxtkl
     * @since 2025/9/29
     */
    public BlockmanEntity(Level level, double x, double y, double z) {
        super(ModEntities.BLOCKMAN.get(), level);
        this.setPos(x, y, z);
    }

    /**
     * 在某个方块位置上创建一个方块人。
     *
     * @param level 维度
     * @param pos 位置
     * @author sxtkl
     * @since 2025/10/3
     */
    public BlockmanEntity(Level level, BlockPos pos) {
        this(level, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d);
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

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BLOCK_STATE, Blocks.DIRT.defaultBlockState());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("BlockState", NbtUtils.writeBlockState(this.entityData.get(BLOCK_STATE)));
    }

    @SuppressWarnings("resource")
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(
            BLOCK_STATE, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK),
                compound.getCompound("BlockState"))
        );
    }

    @Override
    @NotNull
    protected InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof BlockItem item) {
            this.entityData.set(BLOCK_STATE, item.getBlock().defaultBlockState());
        }
        return InteractionResult.PASS;
    }

    @Override
    @Nullable
    @Deprecated
    protected SoundEvent getHurtSound(@NotNull net.minecraft.world.damagesource.DamageSource damageSource) {
        // 返回对应方块的破坏音效作为受到攻击的音效
        return getBlockState().getSoundType().getBreakSound();
    }

    @Override
    @Nullable
    @Deprecated
    protected SoundEvent getDeathSound() {
        // 返回对应方块的破坏音效作为死亡音效
        return getBlockState().getSoundType().getBreakSound();
    }

    @Override
    @Deprecated
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        // 播放对应方块的脚步音效
        SoundEvent stepSound = getBlockState().getSoundType().getStepSound();
        this.playSound(stepSound, 0.15F, 1.0F);
    }

    /**
     * 获取方块状态。
     *
     * @return 方块状态
     * @author sxtkl
     * @since 2025/10/3
     */
    public BlockState getBlockState() {
        return this.entityData.get(BLOCK_STATE);
    }

    /**
     * 设置方块状态。
     *
     * @param state 方块状态
     * @author sxtkl
     * @since 2025/10/3
     */
    public void setBlockState(@NotNull BlockState state) {
        this.entityData.set(BLOCK_STATE, state.getBlock().defaultBlockState());
    }
}
