package com.iicsadog.blockcolony.core.item;

import net.minecraft.world.item.Item;

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
}
