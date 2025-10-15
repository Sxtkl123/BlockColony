package com.iicsadog.blocksblocks.core.data;

import java.util.UUID;

/**
 * ColonyData 类用于存储殖民地的基本信息，包括殖民地的唯一标识符、所有者标识符和名称。
 * 此类作为数据容器，在殖民地管理系统中使用，用于表示一个殖民地的基本属性。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ColonyData {

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
}
