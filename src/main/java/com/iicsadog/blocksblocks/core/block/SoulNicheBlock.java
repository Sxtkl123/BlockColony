package com.iicsadog.blocksblocks.core.block;

import com.iicsadog.blocksblocks.core.block.entity.SoulNicheBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SoulNicheBlock 是一个继承自 BaseEntityBlock 的自定义方块类，用于表示一种名为“魂龛”的特殊方块。
 * 它具有自定义的外观、碰撞形状，以及与自定义方块实体 {@link SoulNicheBlockEntity} 的交互功能。
 *
 * @author sxtkl
 * @since 2025/10/10
 */
public class SoulNicheBlock extends BaseEntityBlock {

    public static final VoxelShape SHAPE_NORTH = Block.box(1.0D, 0.0D, 4.0D, 15.0D, 14.0D, 13.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(1.0D, 0.0D, 3.0D, 15.0D, 14.0D, 12.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(4.0D, 0.0D, 1.0D, 13.0D, 14.0D, 15.0D);
    public static final VoxelShape SHAPE_EAST = Block.box(3.0D, 0.0D, 1.0D, 12.0D, 14.0D, 15.0D);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    /**
     * SoulNicheBlock 的构造方法，用于初始化一个新的魂龛（Soul Niche）方块实例。
     *
     * @author sxtkl
     * @since 2025/10/10
     */
    public SoulNicheBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new SoulNicheBlockEntity(blockPos, blockState);
    }

    @NotNull
    @Override
    protected RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    protected VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
                                  @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    @NotNull
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @NotNull
    @Deprecated
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
