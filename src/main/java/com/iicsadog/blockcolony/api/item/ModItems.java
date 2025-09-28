package com.iicsadog.blockcolony.api.item;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.core.item.SoulItem;
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

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.Items.createItems(BlockColony.MODID);

    public static final DeferredHolder<Item, SoulItem> SOUL_ITEM = ITEMS.register("soul", SoulItem::new);
}
