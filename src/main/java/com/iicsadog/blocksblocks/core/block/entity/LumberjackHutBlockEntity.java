package com.iicsadog.blocksblocks.core.block.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 伐木工小屋方块实体。
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public class LumberjackHutBlockEntity extends BaseHutBlockEntity {

    /**
     * 伐木工小屋方块实体。
     *
     * @author sxtkl
     * @since 2025/11/18
     */
    public LumberjackHutBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LUMBERJACK_HUT_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public ResourceLocation hutType() {
        return BlocksBlocks.namespace("lumberjack");
    }
}
