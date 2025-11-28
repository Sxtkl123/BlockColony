package com.iicsadog.blocksblocks.api.block.entity;

import com.iicsadog.blocksblocks.core.entity.ai.Task;
import com.mojang.serialization.Codec;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class BaseTaskHutBlockEntity<D> extends BaseHutBlockEntity {

    protected final Queue<UUID> taskQueue = new ArrayDeque<>();

    protected final Map<UUID, Task<D>> taskMap = new HashMap<>();

    public BaseTaskHutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    protected abstract Codec<Task<D>> getTaskCodec();

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);

        // taskIds
        ListTag taskQueue = new ListTag();
        this.taskQueue.forEach(id -> taskQueue.add(StringTag.valueOf(id.toString())));
        tag.put("taskQueue", taskQueue);

        // tasks
        CompoundTag taskMap = new CompoundTag();
        this.taskMap.forEach((id, task) -> taskMap.put(id.toString(), task.toNBT(this.getTaskCodec())));
        tag.put("taskMap", taskMap);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("taskQueue")) {
            ListTag taskQueue = tag.getList("taskQueue", Tag.TAG_STRING);
            taskQueue.forEach(element -> {
                if (element instanceof StringTag ele) {
                    this.taskQueue.add(UUID.fromString(ele.getAsString()));
                }
            });
        }
        if (tag.contains("taskMap")) {
            CompoundTag taskMap = tag.getCompound("taskMap");
            for (String key : taskMap.getAllKeys()) {
                this.taskMap.put(UUID.fromString(key), Task.fromNBT(taskMap.getCompound(key), getTaskCodec()));
            }
        }
    }

    public void pushTask(D data) {
        UUID taskId = UUID.randomUUID();
        Task<D> task = new Task<>(taskId, data);
        this.taskQueue.add(taskId);
        this.taskMap.put(taskId, task);
        setChanged();
    }

    public Optional<Task<D>> dequeueTask() {
        if (taskQueue.isEmpty()) {
            return Optional.empty();
        }
        UUID taskId = taskQueue.poll();
        Task<D> task = taskMap.get(taskId);
        setChanged();
        return Optional.of(task);
    }

    public void finishTask(UUID taskId) {
        taskMap.remove(taskId);
        setChanged();
    }

    public void taskFail(UUID taskId) {
        taskQueue.add(taskId);
        setChanged();
    }

}
