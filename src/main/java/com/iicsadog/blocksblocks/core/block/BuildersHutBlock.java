package com.iicsadog.blocksblocks.core.block;

import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.iicsadog.blocksblocks.core.block.entity.BuildersHutBlockEntity;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import com.iicsadog.blocksblocks.core.network.packet.OpenBuildersHutPacket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class BuildersHutBlock extends BaseEntityBlock {
    private static final MapCodec<BuildersHutBlock> CODEC = simpleCodec((properties) -> new BuildersHutBlock());

    public static final VoxelShape SHAPE_NORTH = Block.box(1.0D, 0.0D, 4.0D, 15.0D, 14.0D, 13.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(1.0D, 0.0D, 3.0D, 15.0D, 14.0D, 12.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(4.0D, 0.0D, 1.0D, 13.0D, 14.0D, 15.0D);
    public static final VoxelShape SHAPE_EAST = Block.box(3.0D, 0.0D, 1.0D, 12.0D, 14.0D, 15.0D);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    /**
     * BuildersHutBlock 的构造方法，用于初始化一个新的建筑工小屋（Builders Hut）方块实例。
     *
     * @author arxyt
     * @since 2025/10/27
     */
    public BuildersHutBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @NotNull
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(
            @NotNull ItemStack playerStack, @NotNull BlockState state,
            Level level, @NotNull BlockPos pos, @NotNull Player player,
            @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult
    ) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        ColonyData colony = DataManagers.getInstance(ColonyDataManager::new).getPlayerColony(player.getUUID());

        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof BuildersHutBlockEntity)) {
            return ItemInteractionResult.FAIL;
        }

        Optional<UUID> colonyId = Optional.empty();
        if (colony != null) {
            colonyId = Optional.of(colony.getId());
        }
        ModChannels.NET_CHANNEL.serverHandle(player).send(new OpenBuildersHutPacket(colonyId));
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BuildersHutBlockEntity(blockPos, blockState);
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
