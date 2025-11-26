package com.iicsadog.blocksblocks.core.entity.ai.task;

import com.iicsadog.blocksblocks.core.info.TreeInfo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

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

    public CompoundTag toNBT() {
        return (CompoundTag) CODEC.encodeStart(NbtOps.INSTANCE, this).result().orElse(new CompoundTag());
    }

    public static LumberjackTask fromNBT(CompoundTag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }

    public LumberjackTask withOccupied(boolean occupied) {
        return new LumberjackTask(taskId, treeInfo, occupied);
    }
}
