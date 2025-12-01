package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.ICodecData;
import com.iicsadog.blocksblocks.api.data.IData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;

/**
 * ColonyData 类用于存储殖民地的基本信息，包括殖民地的唯一标识符、所有者标识符和名称。
 * 此类作为数据容器，在殖民地管理系统中使用，用于表示一个殖民地的基本属性。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ColonyData implements ICodecData<ColonyData> {

    private UUID id;

    private UUID ownerId;

    private String name;

    public ColonyData(UUID id, UUID ownerId, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public static final Codec<ColonyData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
        UUIDUtil.CODEC.fieldOf("id").forGetter(ColonyData::getId),
        UUIDUtil.CODEC.fieldOf("ownerId").forGetter(ColonyData::getOwnerId),
        Codec.STRING.fieldOf("name").forGetter(ColonyData::getName)
    ).apply(ins, ColonyData::new));


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
