package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.IData;
import com.iicsadog.blocksblocks.core.components.SoulComponent;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

/**
 * BlockmanData 类实现了 IData 接口，用于管理方块人的数据信息。
 * 该类包含方块人的基本属性，如ID、名称、所属殖民地ID以及接受的和拒绝的方块集合。
 * 提供了从 SoulComponent 创建实例、从 CompoundTag 加载数据以及保存数据到 CompoundTag 的功能。
 *
 * @author sxtkl
 * @since 2025/10/20
 */
public class BlockmanData implements IData<BlockmanData> {

    private UUID id;
    private UUID colonyId;
    private String name;
    private final Set<String> rejectedBlocks = new HashSet<>();
    private final Set<String> acceptedBlocks = new HashSet<>();


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
        BlockmanData blockmanData = new BlockmanData();
        blockmanData.id = soul.id();
        blockmanData.name = soul.name();
        blockmanData.rejectedBlocks.addAll(soul.rejectedBlocks());
        blockmanData.acceptedBlocks.addAll(soul.acceptedBlocks());
        return blockmanData;
    }

    /**
     * 从 CompoundTag 中加载 BlockmanData 对象的数据。
     * 此方法会从提供的 CompoundTag 中提取方块人的ID、名称以及接受的和拒绝的方块集合，
     * 并将这些数据设置到当前对象中。
     *
     * @param tag 包含方块人数据的 CompoundTag 对象，应包含"id"、"name"、"rejectedBlocks"和"acceptedBlocks"等键
     * @return 当前 BlockmanData 对象，已加载了从 CompoundTag 中提取的数据
     * @author sxtkl
     * @since 2025/10/20
     */
    public BlockmanData load(final CompoundTag tag) {
        this.id = tag.getUUID("id");
        this.name = tag.getString("name");
        this.rejectedBlocks.clear();
        ListTag rejectedBlocksList = tag.getList("rejectedBlocks", Tag.TAG_STRING);
        rejectedBlocksList.forEach(element -> {
            if (element instanceof StringTag) {
                this.rejectedBlocks.add(element.getAsString());
            }
        });
        this.acceptedBlocks.clear();
        ListTag acceptedBlocksList = tag.getList("acceptedBlocks", Tag.TAG_STRING);
        acceptedBlocksList.forEach(element -> {
            if (element instanceof StringTag) {
                this.acceptedBlocks.add(element.getAsString());
            }
        });
        return this;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putUUID("id", this.id);
        tag.putString("name", this.name);
        ListTag rejectedBlocks = new ListTag();
        this.rejectedBlocks.forEach(block -> rejectedBlocks.add(StringTag.valueOf(block)));
        ListTag acceptedBlocks = new ListTag();
        this.acceptedBlocks.forEach(block -> acceptedBlocks.add(StringTag.valueOf(block)));
        tag.put("rejected_blocks", rejectedBlocks);
        tag.put("accepted_blocks", acceptedBlocks);
        return tag;
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
}
