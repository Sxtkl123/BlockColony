package com.iicsadog.blocksblocks.api.item;

import com.iicsadog.blocksblocks.BlocksBlocks;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 管理模组的创造模式标签。
 * 这个类负责创建和注册模组的创造模式标签，用于在创造模式物品栏中显示模组的物品。
 *
 * @author sxtkl
 * @since 2025/10/22
 */
public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlocksBlocks.MODID);

    public static final Supplier<CreativeModeTab>
        BLOCKS_BLOCKS_TAB  = CREATIVE_MODE_TABS.register("blocks_blocks_tab", () -> CreativeModeTab.builder()
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .title(Component.translatable("creative_mode_tab.blocks_blocks_tab.title"))
        .icon(() -> ModItems.SOUL_ITEM.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(ModItems.SOUL_ITEM.get());
            output.accept(ModItems.SOUL_NICHE_BLOCK_ITEM.get());
            output.accept(ModItems.BUILDERS_HUT_BLOCK_ITEM.get());
            output.accept(ModItems.LUMBERJACK_HUT_BLOCK_ITEM.get());
        })
        .build());
}
