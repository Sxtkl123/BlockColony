package com.iicsadog.blocksblocks.core.entity.ai;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class Task<D> {

    private final UUID taskId;

    private final D data;

    public Task(UUID taskId, D data) {
        this.taskId = taskId;
        this.data = data;
    }

    public static <D> Codec<Task<D>> createCodec(Codec<D> dataCodec) {
        return RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("taskId").forGetter(Task::taskId),
            dataCodec.fieldOf("data").forGetter(Task::data)
        ).apply(instance, Task::new));
    }

    public UUID taskId() {
        return taskId;
    }

    public D data() {
        return data;
    }

    public CompoundTag toNBT(Codec<Task<D>> dataCodec) {
        return (CompoundTag) dataCodec.encodeStart(NbtOps.INSTANCE, this).result().orElse(new CompoundTag());
    }

    public static <D> Task<D> fromNBT(CompoundTag tag, Codec<Task<D>> dataCodec) {
        return dataCodec.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }
}
