package com.iicsadog.blocksblocks.api.block;

import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 基础的工作小屋实体方块。
 *
 * @author sxtkl
 * @since 2025/11/7
 */
public abstract class BaseHutEntityBlock extends BaseEntityBlock {
    /**
     * 基础的工作小屋实体方块。
     *
     * @param properties 属性
     * @author sxtkl
     * @since 2025/11/7
     */
    protected BaseHutEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                            @NotNull BlockState newState, boolean movedByPiston) {
        if (level.isClientSide) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BaseHutBlockEntity entity)) {
            return;
        }
        if (entity.getBuildingId() == null) {
            return;
        }
        DataManagers.getInstance(BuildingDataManager::new).delete(entity.getBuildingId());
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
