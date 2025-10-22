package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.IData;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;

/**
 * BuildingData 类实现了 IData 接口，用于存储建筑物的基本信息。
 * 该类包含建筑物的唯一标识符、所属殖民地ID、建筑类型和等级等基本属性。
 * 提供了从 CompoundTag 加载数据以及保存数据到 CompoundTag 的功能。
 *
 * @author sxtkl
 * @since 2025/10/22
 */
public class BuildingData implements IData<BuildingData> {
    private UUID id;
    private UUID colonyId;
    private String type;
    private byte level;

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putUUID("id", id);
        tag.putUUID("colony_id", colonyId);
        tag.putString("type", type);
        tag.putByte("level", level);
        return tag;
    }

    @Override
    public BuildingData load(CompoundTag tag) {
        this.id = tag.getUUID("id");
        this.colonyId = tag.getUUID("colony_id");
        this.type = tag.getString("type");
        this.level = tag.getByte("level");
        return this;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getColonyId() {
        return colonyId;
    }

    public void setColonyId(UUID colonyId) {
        this.colonyId = colonyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }
}
