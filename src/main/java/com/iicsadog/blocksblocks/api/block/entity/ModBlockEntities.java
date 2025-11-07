package com.iicsadog.blocksblocks.api.block.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.block.ModBlocks;
import com.iicsadog.blocksblocks.core.block.entity.BuildersHutBlockEntity;
import com.iicsadog.blocksblocks.core.block.entity.LumberjackHutBlockEntity;
import com.iicsadog.blocksblocks.core.block.entity.SoulNicheBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 存储所有的方块实体类。
 *
 * @author sxtkl
 * @since 2025/10/10
 */
@SuppressWarnings("DataFlowIssue")
public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulNicheBlockEntity>>
        SOUL_NICHE_BLOCK_ENTITY = BLOCK_ENTITIES.register("soul_niche", () -> BlockEntityType.Builder.of(
            SoulNicheBlockEntity::new,
            ModBlocks.SOUL_NICHE_BLOCK.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BuildersHutBlockEntity>>
        BUILDING_HUT_BLOCK_ENTITY = BLOCK_ENTITIES.register("builders_hut", () -> BlockEntityType.Builder.of(
                BuildersHutBlockEntity::new,
                ModBlocks.BUILDERS_HUT_BLOCK.get()
        ).build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LumberjackHutBlockEntity>>
        LUMBERJACK_HUT_BLOCK_ENTITY = BLOCK_ENTITIES.register("lumberjack_hut", () -> BlockEntityType.Builder.of(
                LumberjackHutBlockEntity::new,
                ModBlocks.LUMBERJACK_HUT_BLOCK.get()
        ).build(null)
    );

}
