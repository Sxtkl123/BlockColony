package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import com.iicsadog.blocksblocks.core.block.SoulNicheBlock;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * {@code SoulNicheBlockEntity} 是一个继承自 {@link BlockEntity} 的自定义方块实体类，
 * 用于为 {@link SoulNicheBlock} 提供存储和功能支持。它负责管理与该方块实体关联的元数据，
 *
 * @author sxtkl
 * @since 2025/10/10
 */
public class SoulNicheBlockEntity extends BlockEntity {

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
    public SoulNicheBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOUL_NICHE_BLOCK_ENTITY.get(), pos, blockState);
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
