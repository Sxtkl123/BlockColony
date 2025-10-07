package com.iicsadog.blocksblocks.api.item;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.item.SoulItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 模组的所有物品都在这里注册。
 *
 * @author sxtkl
 * @since 2025/9/27
 */
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.Items.createItems(
        BlocksBlocks.MODID);

    public static final DeferredHolder<Item, SoulItem> SOUL_ITEM = ITEMS.register("soul", SoulItem::new);
}
