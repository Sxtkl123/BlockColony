package com.iicsadog.blocksblocks.core.entity.ai.task;

import com.iicsadog.blocksblocks.core.info.TreeInfo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

/**
 * 一个伐木任务。
 *
 * @param taskId 任务ID
 * @param treeInfo 树信息
 * @param occupied 是否被接受
 *
 * @author sxtkl
 * @since 2025/11/27
 */
public record LumberjackTask(
    UUID taskId,
    TreeInfo treeInfo,
    boolean occupied
) {

    public static final Codec<LumberjackTask> CODEC =  RecordCodecBuilder.create(instance ->
        instance.group(
            UUIDUtil.CODEC.fieldOf("taskId").forGetter(LumberjackTask::taskId),
            TreeInfo.CODEC.fieldOf("treeInfo").forGetter(LumberjackTask::treeInfo),
            Codec.BOOL.fieldOf("occupied").forGetter(LumberjackTask::occupied)
        ).apply(instance, LumberjackTask::new)
    );

    /**
     * 序列化为NBT。
     *
     * @return 序列化后的NBT标签
     * @author sxtkl
     * @since 2025/11/27
     */
    public CompoundTag toNBT() {
        return (CompoundTag) CODEC.encodeStart(NbtOps.INSTANCE, this).result().orElse(new CompoundTag());
    }

    /**
     * 从NBT中反序列化。
     *
     * @param tag NBT标签
     * @return 反序列化后的实例
     * @author sxtkl
     * @since 2025/11/27
     */
    public static LumberjackTask fromNBT(CompoundTag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }

    /**
     * 设置任务接收状态，该操作会返回一个新的实例。
     *
     * @param occupied 接收状态
     * @return 新的任务实例
     * @author sxtkl
     * @since 2025/11/27
     */
    public LumberjackTask withOccupied(boolean occupied) {
        return new LumberjackTask(taskId, treeInfo, occupied);
    }
}
