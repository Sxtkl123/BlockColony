package com.iicsadog.blocksblocks.core.entity;

import com.google.common.collect.ImmutableList;
import com.iicsadog.blocksblocks.api.ModRegistries;
import com.iicsadog.blocksblocks.api.ai.ModMemoryModuleTypes;
import com.iicsadog.blocksblocks.api.ai.ModSensors;
import com.iicsadog.blocksblocks.api.entity.ModEntities;
import com.iicsadog.blocksblocks.api.job.Job;
import com.iicsadog.blocksblocks.api.job.ModJobs;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.manager.common.BlockmanEntityCacheManager;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
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

    private static final EntityDataAccessor<Optional<UUID>>
        BLOCKMAN_ID = SynchedEntityData.defineId(BlockmanEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private Job job;

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.PATH,
        ModMemoryModuleTypes.HUT_ID.get(),
        ModMemoryModuleTypes.LUMBERJACK_TASK.get(),
        ModMemoryModuleTypes.STATUS.get()
    );

    protected static final ImmutableList<SensorType<? extends Sensor<? super BlockmanEntity>>> SENSOR_TYPES = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        ModSensors.BLOCKMAN_HUT.get(),
        ModSensors.LUMBERJACK_TASK.get(),
        ModSensors.CLOSE_ENOUGH_TO_TREE.get()
    );

    private BlockmanData blockmanData;

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

    /**
     * 方块酱属性构建器，生命值为20，速度为0.2。
     *
     * @return 属性构造器
     * @author sxtkl
     * @since 2025/9/29
     */
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20)
            .add(Attributes.MOVEMENT_SPEED, 0.2F);
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
        builder.define(BLOCKMAN_ID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("block_state", NbtUtils.writeBlockState(this.entityData.get(BLOCK_STATE)));
        this.entityData.get(BLOCKMAN_ID).ifPresent(uuid -> compound.putUUID("blockman_id", uuid));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(
            BLOCK_STATE, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK),
                compound.getCompound("block_state"))
        );
        setBlockmanId(compound.getUUID("blockman_id"));
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
    @Deprecated
    protected SoundEvent getHurtSound(@NotNull net.minecraft.world.damagesource.DamageSource damageSource) {
        // 返回对应方块的破坏音效作为受到攻击的音效
        return getBlockState().getSoundType().getBreakSound();
    }

    @Override
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

    @NotNull
    @Override
    protected Brain.Provider<BlockmanEntity> brainProvider() {
        return getJob().getProvider();
    }

    @NotNull
    @Override
    protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        return getJob().makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public Brain<BlockmanEntity> getBrain() {
        return (Brain<BlockmanEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("blockmanBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("blockmanActivityUpdate");
        getJob().updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    public UUID getBlockmanId() {
        return entityData.get(BLOCKMAN_ID).orElse(null);
    }

    /**
     * 设置方块人实体的方块人id。
     *
     * @param blockmanId 方块人id
     * @author sxtkl
     * @since 2025/11/19
     */
    public void setBlockmanId(UUID blockmanId) {
        entityData.set(BLOCKMAN_ID, Optional.of(blockmanId));
        BlockmanEntityCacheManager.getInstance().getCache().put(blockmanId, this);
    }

    public Job getJob() {
        if (this.job == null) {
            BlockmanData data = getBlockmanData();
            if (data == null) {
                this.job = ModJobs.EMPTY.get();
            } else {
                this.job = ModRegistries.JOB.get(data.getJob());
            }
        }
        return job;
    }

    public void updateBrainByJob() {
        BlockmanData data = getBlockmanData();
        this.job = ModRegistries.JOB.get(data.getJob());
        if (this.job != null) {
            this.brain = job.getBrain(this);
        }
    }

    public BlockmanData getBlockmanData() {
        if (blockmanData == null) {
            UUID blockmanId = getBlockmanId();
            if (blockmanId != null) {
                blockmanData = DataManagers.getInstance(BlockmanDataManager::new).query(blockmanId);
            }
        }
        return blockmanData;
    }
}
