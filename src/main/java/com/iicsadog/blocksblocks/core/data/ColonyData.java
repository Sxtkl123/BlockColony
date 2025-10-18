package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.IData;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;

/**
 * ColonyData 类用于存储殖民地的基本信息，包括殖民地的唯一标识符、所有者标识符和名称。
 * 此类作为数据容器，在殖民地管理系统中使用，用于表示一个殖民地的基本属性。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ColonyData implements IData {

    private UUID id;

    private UUID ownerId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "ColonyData{id=" + id + ", ownerId=" + ownerId + ", name='" + name + "'}";
    }

    /**
     * 从 CompoundTag 中加载殖民地数据。
     *
     * @param tag 包含殖民地数据的 CompoundTag 对象
     * @return 包含加载的殖民地数据的 ColonyData 对象
     * @author sxtkl
     * @since 2025/10/17
     */
    public static ColonyData load(final CompoundTag tag) {
        ColonyData data = new ColonyData();
        data.setId(tag.getUUID("id"));
        data.setOwnerId(tag.getUUID("ownerId"));
        data.setName(tag.getString("name"));
        return data;
    }


    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putUUID("id", this.id);
        tag.putString("name", this.name);
        tag.putUUID("ownerId", this.ownerId);
        return tag;
    }
}
