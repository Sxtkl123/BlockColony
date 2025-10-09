package com.iicsadog.blocksblocks.core.item;

import com.iicsadog.blocksblocks.api.component.ModComponents;
import com.iicsadog.blocksblocks.api.item.ISoulItemAbility;
import com.iicsadog.blocksblocks.core.components.Blockmen;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
public class SoulItem extends Item implements ISoulItemAbility {

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
    public Component getName(@NotNull ItemStack stack) {
        Blockmen blockmen = stack.get(ModComponents.BLOCKMEN);
        if (blockmen != null && blockmen.name() != null && !blockmen.name().trim().isEmpty()) {
            return Component.translatable("item.blocks_blocks.someones_soul", blockmen.name());
        }
        return super.getName(stack);
    }

    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockState state = level.getBlockState(context.getClickedPos());
        float hardness = state.getDestroySpeed(level, context.getClickedPos());
        if (hardness == -1.0F) {
            return InteractionResult.PASS;
        }
        ItemStack stack = context.getItemInHand();
        Blockmen record = stack.get(ModComponents.BLOCKMEN);
        if (record == null) {
            return InteractionResult.PASS;
        }
        String blockKey = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        if (!this.possess(stack, blockKey, level.random)) {
            if (context.getPlayer() != null) {
                context.getPlayer().sendSystemMessage(Component.translatable("message.blocks_blocks.reject"));
            }
            return InteractionResult.PASS;
        }
        BlockmanEntity blockman = new BlockmanEntity(level, context.getClickedPos());
        if (record.name() != null) {
            blockman.setCustomName(Component.literal(record.name()));
            blockman.setCustomNameVisible(true);
        }
        level.setBlock(context.getClickedPos(), Blocks.AIR.defaultBlockState(), 1);
        level.addFreshEntity(blockman);
        blockman.setBlockState(state);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean possess(ItemStack stack, String blockKey, RandomSource random) {
        Blockmen blockmen = stack.get(ModComponents.BLOCKMEN);
        if (blockmen == null) {
            return false;
        }
        if (blockmen.isAccepted(blockKey)) {
            return true;
        }
        if (blockmen.isRejected(blockKey)) {
            return false;
        }
        if (random.nextDouble() <= 0.3D) {
            blockmen = blockmen.withAcceptedBlock(blockKey);
            stack.set(ModComponents.BLOCKMEN, blockmen);
            return true;
        }
        blockmen = blockmen.withRejectedBlock(blockKey);
        stack.set(ModComponents.BLOCKMEN, blockmen);
        return false;
    }
}
