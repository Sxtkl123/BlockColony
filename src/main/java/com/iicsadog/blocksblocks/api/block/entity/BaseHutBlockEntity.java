package com.iicsadog.blocksblocks.api.block.entity;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 基础小屋方块实体。
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public abstract class BaseHutBlockEntity extends BlockEntity {

    @Nullable
    protected UUID buildingId = null;

    /**
     * 基础小屋方块实体。
     *
     * @author sxtkl
     * @since 2025/11/18
     */
    public BaseHutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.buildingId != null) {
            tag.putUUID("building_id", this.buildingId);
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("building_id")) {
            this.buildingId = tag.getUUID("building_id");
        }
    }

    public void setBuildingId(@Nullable UUID id) {
        this.buildingId = id;
    }

    @Nullable
    public UUID getBuildingId() {
        return buildingId;
    }

    /**
     * 小屋方块类型，使用命名空间避免冲突。
     *
     * @return 小屋方块类型
     * @author sxtkl
     * @since 2025/11/18
     */
    public abstract ResourceLocation hutType();
}
