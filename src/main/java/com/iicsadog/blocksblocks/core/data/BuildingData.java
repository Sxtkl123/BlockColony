package com.iicsadog.blocksblocks.core.data;

import com.iicsadog.blocksblocks.api.data.ICodecData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * BuildingData 类实现了 IData 接口，用于存储建筑物的基本信息。
 * 该类包含建筑物的唯一标识符、所属殖民地ID、建筑类型和等级等基本属性。
 * 提供了从 CompoundTag 加载数据以及保存数据到 CompoundTag 的功能。
 *
 * @author sxtkl
 * @since 2025/10/22
 */
public class BuildingData implements ICodecData<BuildingData> {
    private UUID id;
    private UUID colonyId;
    private String type;
    private int rank;
    private String dimension;
    private BlockPos pos;

    public static final Codec<BuildingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        UUIDUtil.CODEC.fieldOf("id").forGetter(BuildingData::getId),
        UUIDUtil.CODEC.fieldOf("colonyId").forGetter(BuildingData::getColonyId),
        Codec.STRING.fieldOf("type").forGetter(BuildingData::getType),
        Codec.INT.fieldOf("rank").forGetter(BuildingData::getRank),
        Codec.STRING.fieldOf("dimension").forGetter(BuildingData::getDimension),
        BlockPos.CODEC.fieldOf("pos").forGetter(BuildingData::getPos)
    ).apply(instance, BuildingData::new));

    /**
     * 建筑物数据。
     *
     * @author sxtkl
     * @since 2025/12/1
     */
    public BuildingData(
        UUID id,
        UUID colonyId,
        String type,
        int rank,
        String dimension,
        BlockPos pos
    ) {
        this.id = id;
        this.colonyId = colonyId;
        this.type = type;
        this.rank = rank;
        this.dimension = dimension;
        this.pos = pos;
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

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDimension() {
        return dimension;
    }

    /**
     * 获取建筑物所在维度。
     *
     * @param server 服务器
     * @return 建筑物服务器维度
     * @author sxtkl
     * @since 2025/11/18
     */
    public ServerLevel getDimension(MinecraftServer server) {
        ResourceLocation location = ResourceLocation.parse(this.dimension);
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, location);
        return server.getLevel(dimension);
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setDimension(ServerLevel level) {
        this.dimension = level.dimension().location().toString();
    }
}
