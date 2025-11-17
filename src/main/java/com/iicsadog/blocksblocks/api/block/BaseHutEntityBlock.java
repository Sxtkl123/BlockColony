package com.iicsadog.blocksblocks.api.block;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
    @NotNull
    protected ItemInteractionResult useItemOn(
        @NotNull ItemStack playerStack, @NotNull BlockState state,
        Level level, @NotNull BlockPos pos, @NotNull Player player,
        @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult
    ) {
        if (!level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        ModRequests.getCheckHutRequest(pos)
            .success(response -> Minecraft.getInstance().setScreen(getScreen(response.buildingId())))
            .fail(BlocksBlocks.LOGGER::info)
            .send();
        return ItemInteractionResult.SUCCESS;
    }

    /**
     * 获取开启后的GUI屏幕。
     *
     * @param buildingId 建筑Id
     * @return GUI
     * @author sxtkl
     * @since 2025/11/17
     */
    public abstract Screen getScreen(UUID buildingId);

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
