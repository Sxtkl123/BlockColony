package com.iicsadog.blocksblocks.core.entity.ai;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

/**
 * 任务系统的任务实例。
 *
 * @author sxtkl
 * @since 2025/11/28
 */
public record Task<D>(UUID taskId, D data) {

    /**
     * 创建一个任务的Codec。
     *
     * @param dataCodec 数据的Codec
     * @return 任务的Codec
     * @author sxtkl
     * @since 2025/11/28
     */
    public static <D> Codec<Task<D>> createCodec(Codec<D> dataCodec) {
        return RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("taskId").forGetter(Task::taskId),
            dataCodec.fieldOf("data").forGetter(Task::data)
        ).apply(instance, Task::new));
    }

    /**
     * 将任务转化为NBT。
     *
     * @param dataCodec 数据的Codec
     * @return NBT标签
     * @author sxtkl
     * @since 2025/11/28
     */
    public CompoundTag toNBT(Codec<Task<D>> dataCodec) {
        return (CompoundTag) dataCodec.encodeStart(NbtOps.INSTANCE, this).result()
            .orElse(new CompoundTag());
    }

    /**
     * 从NBT中将任务反序列化。
     *
     * @param tag NBT标签
     * @param dataCodec 数据的Codec
     * @return 反序列化的任务
     * @author sxtkl
     * @since 2025/11/28
     */
    public static <D> Task<D> fromNBT(CompoundTag tag, Codec<Task<D>> dataCodec) {
        return dataCodec.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }
}
