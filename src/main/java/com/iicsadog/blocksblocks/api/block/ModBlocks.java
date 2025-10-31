package com.iicsadog.blocksblocks.api.block;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.block.BuildersHutBlock;
import com.iicsadog.blocksblocks.core.block.SoulNicheBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 存储所有的方块类。
 *
 * @author sxtkl
 * @since 2025/10/10
 */
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(Registries.BLOCK, BlocksBlocks.MODID);

    public static final DeferredHolder<Block, SoulNicheBlock> SOUL_NICHE_BLOCK =
        BLOCKS.register("soul_niche", SoulNicheBlock::new);

    public static final DeferredHolder<Block, BuildersHutBlock> BUILDERS_HUT_BLOCK =
        BLOCKS.register("builders_hut", BuildersHutBlock::new);

}
