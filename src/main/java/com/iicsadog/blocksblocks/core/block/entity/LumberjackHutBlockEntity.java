package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.core.entity.ai.task.LumberjackTask;
import com.iicsadog.blocksblocks.core.info.TreeInfo;
import com.iicsadog.blocksblocks.core.worker.TreeRetrieverWorker;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.WorldWorkerManager;
import org.jetbrains.annotations.NotNull;

/**
 * 伐木工小屋方块实体。
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public class LumberjackHutBlockEntity extends BaseHutBlockEntity {

    public static final ResourceLocation HUT_TYPE = BlocksBlocks.namespace("lumberjack");

    private static final int SCAN_RATE = 100;

    private int timeToTick = SCAN_RATE;

    private TreeRetrieverWorker worker;

    private final Queue<UUID> taskIds = new ArrayDeque<>();

    private final Map<UUID, LumberjackTask> tasks = new HashMap<>();

    /**
     * 伐木工小屋方块实体。
     *
     * @author sxtkl
     * @since 2025/11/18
     */
    public LumberjackHutBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LUMBERJACK_HUT_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public ResourceLocation hutType() {
        return HUT_TYPE;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);

        // taskIds
        ListTag taskIds = new ListTag();
        this.taskIds.forEach(id -> taskIds.add(StringTag.valueOf(id.toString())));
        tag.put("taskIds", taskIds);

        // tasks
        CompoundTag tasks = new CompoundTag();
        this.tasks.forEach((id, task) -> tasks.put(id.toString(), task.toNBT()));
        tag.put("tasks", tasks);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("taskIds")) {
            ListTag tasks = tag.getList("taskIds", Tag.TAG_STRING);
            tasks.forEach(element -> {
                if (element instanceof StringTag ele) {
                    this.taskIds.add(UUID.fromString(ele.getAsString()));
                }
            });
        }
        if (tag.contains("tasks")) {
            CompoundTag tasks = tag.getCompound("tasks");
            for (String key : tasks.getAllKeys()) {
                this.tasks.put(UUID.fromString(key), LumberjackTask.fromNBT(tasks.getCompound(key)));
            }
        }
    }

    /**
     * 每个tick会触发的内容。
     *
     * @param level 维度
     * @param pos 方块位置
     * @author sxtkl
     * @since 2025/11/27
     */
    public void tick(ServerLevel level, BlockPos pos) {
        if (--timeToTick > 0L) {
            return;
        }
        timeToTick = SCAN_RATE;
        if (worker == null || !worker.hasWork()) {
            worker = new TreeRetrieverWorker(pos.offset(-8, -1, -8), pos.offset(8, 1, 8), level, this);
            tasks.forEach((ignore, task) -> worker.getVisited().addAll(task.treeInfo().trunk()));
            WorldWorkerManager.addWorker(worker);
        }
    }

    /**
     * 把一棵树加入到代办任务表中。
     *
     * @param info 树的信息
     * @author sxtkl
     * @since 2025/11/27
     */
    public void pushTree(TreeInfo info) {
        UUID taskId = UUID.randomUUID();
        LumberjackTask task = new LumberjackTask(taskId, info, false);
        this.tasks.put(taskId, task);
        this.taskIds.add(taskId);
        setChanged();
    }

    /**
     * 获取一个任务，如果任务列表已经空了，则会返回empty。
     *
     * @return 获取到的任务
     * @author sxtkl
     * @since 2025/11/27
     */
    public Optional<LumberjackTask> dequeueTask() {
        if (taskIds.isEmpty()) {
            return Optional.empty();
        }
        UUID taskId = taskIds.poll();
        LumberjackTask task = tasks.get(taskId).withOccupied(true);
        tasks.put(taskId, task);
        setChanged();
        return Optional.of(task);
    }

    /**
     * 完成任务。
     *
     * @param taskId 任务ID
     * @author sxtkl
     * @since 2025/11/27
     */
    public void finishTask(UUID taskId) {
        tasks.remove(taskId);
        setChanged();
    }
}
