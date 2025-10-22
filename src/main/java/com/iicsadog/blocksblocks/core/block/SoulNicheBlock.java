package com.iicsadog.blocksblocks.core.block;

import com.iicsadog.blocksblocks.api.component.ModComponents;
import com.iicsadog.blocksblocks.api.item.ModItems;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.iicsadog.blocksblocks.core.block.entity.SoulNicheBlockEntity;
import com.iicsadog.blocksblocks.core.components.SoulComponent;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import com.iicsadog.blocksblocks.core.network.packet.OpenSoulNichePacket;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.phys.BlockHitResult;
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

    private static final MapCodec<SoulNicheBlock> CODEC = simpleCodec((properties) -> new SoulNicheBlock());

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

        // 手持灵魂时优先执行绑定逻辑
        if (playerStack.is(ModItems.SOUL_ITEM)) {
            return processBind(player, colony, playerStack);
        }
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof SoulNicheBlockEntity)) {
            return ItemInteractionResult.FAIL;
        }
        ModChannels.NET_CHANNEL.serverHandle(player).send(new OpenSoulNichePacket(colony == null));
        return ItemInteractionResult.SUCCESS;
    }

    @Deprecated
    private static boolean processShowBlockmen(ColonyData colony, Player player) {
        if (colony == null) {
            return false;
        }
        // 使用国际化消息替换硬编码文本
        Component headerMessage = Component.translatable("message.blocks_blocks.colony_have", colony.getName());
        player.sendSystemMessage(headerMessage);

        BlockmanDataManager manager = DataManagers.getInstance(BlockmanDataManager::new);
        List<UUID> blockmanIds = manager.getColonyBlockmen(colony.getId());
        for (UUID blockmenId : blockmanIds) {
            BlockmanData data = manager.getBlockmanData(blockmenId);
            if (data != null) {
                // 使用国际化消息替换硬编码文本
                Component itemMessage = Component.translatable("message.blocks_blocks.blockman_list_item", data.getName());
                player.sendSystemMessage(itemMessage);
            }
        }
        return true;
    }

    private static ItemInteractionResult processBind(Player player, ColonyData colony, ItemStack playerStack) {
        if (colony == null) {
            player.sendSystemMessage(Component.translatable("message.blocks_blocks.no_colony"));
            return ItemInteractionResult.FAIL;
        }
        SoulComponent soulComponent = playerStack.get(ModComponents.BLOCKMEN);
        if (soulComponent == null || soulComponent.id() == null) {
            player.sendSystemMessage(Component.translatable("message.blocks_blocks.empty_soul"));
            return ItemInteractionResult.FAIL;
        }
        BlockmanDataManager manager = DataManagers.getInstance(BlockmanDataManager::new);

        // 如果该方块人有了其对应的殖民地ID，说明该方块人已经被绑定到了某个殖民地
        BlockmanData blockman = manager.getBlockmanData(soulComponent.id());
        if (blockman != null && blockman.getColonyId() != null) {
            UUID bindColonyId = blockman.getColonyId();
            ColonyData bindColony = DataManagers.getInstance(ColonyDataManager::new).getColony(bindColonyId);
            if (bindColony != null) {
                player.sendSystemMessage(Component.translatable("message.blocks_blocks.soul_bound", bindColony.getName()));
                return ItemInteractionResult.FAIL;
            }
        }

        blockman = BlockmanData.fromSoul(soulComponent);
        blockman.setColonyId(colony.getId());
        manager.bind(blockman);
        player.sendSystemMessage(Component.translatable("message.blocks_blocks.soul_bind_success", soulComponent.name(), colony.getName()));
        return ItemInteractionResult.SUCCESS;
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
