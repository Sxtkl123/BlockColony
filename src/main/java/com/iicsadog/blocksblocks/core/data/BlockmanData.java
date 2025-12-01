package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.ICodecData;
import com.iicsadog.blocksblocks.api.job.ModJobs;
import com.iicsadog.blocksblocks.core.component.SoulComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * BlockmanData 类实现了 IData 接口，用于管理方块人的数据信息。
 * 该类包含方块人的基本属性，如ID、名称、所属殖民地ID以及接受的和拒绝的方块集合。
 * 提供了从 SoulComponent 创建实例、从 CompoundTag 加载数据以及保存数据到 CompoundTag 的功能。
 *
 * @author sxtkl
 * @since 2025/10/20
 */
public class BlockmanData implements ICodecData<BlockmanData> {

    private UUID id;
    private UUID colonyId;
    private UUID workFor;
    private ResourceLocation job;
    private String name;
    private final Set<String> rejectedBlocks = new HashSet<>();
    private final Set<String> acceptedBlocks = new HashSet<>();

    public static final Codec<BlockmanData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        UUIDUtil.CODEC.fieldOf("id").forGetter(BlockmanData::getId),
        UUIDUtil.CODEC.fieldOf("colonyId").forGetter(BlockmanData::getColonyId),
        UUIDUtil.CODEC.optionalFieldOf("workFor").forGetter(data -> Optional.ofNullable(data.getWorkFor())),
        ResourceLocation.CODEC.fieldOf("job").forGetter(BlockmanData::getJob),
        Codec.STRING.fieldOf("name").forGetter(BlockmanData::getName),
        Codec.STRING.listOf().xmap(Set::copyOf, ArrayList::new)
            .fieldOf("rejectedBlocks").forGetter(BlockmanData::getRejectedBlocks),
        Codec.STRING.listOf().xmap(Set::copyOf, ArrayList::new)
            .fieldOf("acceptedBlocks").forGetter(BlockmanData::getAcceptedBlocks)
    ).apply(instance, (id, colonyId, workForOpt, job, name, rejectedBlocks, acceptedBlocks) ->
        new BlockmanData(id, colonyId, workForOpt.orElse(null), job, name, rejectedBlocks, acceptedBlocks)));

    /**
     * 方块人数据。
     *
     * @author sxtkl
     * @since 2025/12/1
     */
    public BlockmanData(
        UUID id,
        UUID colonyId,
        UUID workFor,
        ResourceLocation job,
        String name,
        Set<String> rejectedBlocks,
        Set<String> acceptedBlocks
    ) {
        this.id = id;
        this.colonyId = colonyId;
        this.workFor = workFor;
        this.job = job;
        this.name = name;
        this.rejectedBlocks.addAll(rejectedBlocks);
        this.acceptedBlocks.addAll(acceptedBlocks);
    }

    /**
     * 从 SoulComponent 对象创建 BlockmanData 实例，
     * 此方法将 SoulComponent 中的数据复制到新的 BlockmanData 对象中。
     *
     * @param soul 包含方块人数据的 SoulComponent 对象
     * @return 包含相同数据的 BlockmanData 实例
     * @author sxtkl
     * @since 2025/10/20
     */
    public static BlockmanData fromSoul(SoulComponent soul) {
        return new BlockmanData(soul.id(), null, null, ModJobs.EMPTY.getId(), soul.name(), soul.rejectedBlocks(), soul.acceptedBlocks());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRejectedBlocks() {
        return rejectedBlocks;
    }

    public Set<String> getAcceptedBlocks() {
        return acceptedBlocks;
    }

    public UUID getColonyId() {
        return colonyId;
    }

    public void setColonyId(UUID colonyId) {
        this.colonyId = colonyId;
    }

    @Nullable
    public UUID getWorkFor() {
        return workFor;
    }

    public void setWorkFor(@Nullable UUID workFor) {
        this.workFor = workFor;
    }

    public ResourceLocation getJob() {
        return job;
    }

    public void setJob(ResourceLocation job) {
        this.job = job;
    }
}
