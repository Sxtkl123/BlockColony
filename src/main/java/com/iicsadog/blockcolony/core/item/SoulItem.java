package com.iicsadog.blockcolony.core.item;

import com.iicsadog.blockcolony.core.entity.BlockmanEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 灵魂物品。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
public class SoulItem extends Item {
    /**
     * 灵魂物品。
     *
     * @since 2025/9/27
     * @author sxt
     */
    public SoulItem() {
        super(new Properties().stacksTo(1).fireResistant());
    }

    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        float hardness = state.getDestroySpeed(level, context.getClickedPos());
        if (hardness == -1.0F) {
            return InteractionResult.PASS;
        }
        BlockmanEntity blockman = new BlockmanEntity(level, context.getClickedPos());
        level.setBlock(context.getClickedPos(), Blocks.AIR.defaultBlockState(), 1);
        level.addFreshEntity(blockman);
        blockman.setBlockState(state);
        return InteractionResult.SUCCESS;
    }
}
