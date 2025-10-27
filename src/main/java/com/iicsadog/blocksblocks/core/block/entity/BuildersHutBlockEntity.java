package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class BuildersHutBlockEntity extends BlockEntity {
    @Nullable
    private UUID id = null;

    /**
     * {@code SoulNicheBlockEntity} 的构造方法，用于初始化魂龛（Soul Niche）方块实体的实例。
     * 该方法接受方块的位置和方块的状态作为参数，并通过父类的构造方法完成初始化。
     *
     * @param pos        该方块实体的世界位置。
     * @param blockState 当前方块的状态信息。
     *
     * @author sxtkl
     * @since 2025/10/10
     **/
    public BuildersHutBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BUILDING_HUT_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.id != null) {
            tag.putUUID("id", this.id);
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("id")) {
            this.id = tag.getUUID("id");
        }
    }

    public void setId(@Nullable UUID id) {
        this.id = id;
    }

    @Nullable
    public UUID getId() {
        return id;
    }
}
