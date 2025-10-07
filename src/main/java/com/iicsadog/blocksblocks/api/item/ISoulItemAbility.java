package com.iicsadog.blocksblocks.api.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * 灵魂物品的额外能力。
 *
 * @author sxtkl
 * @since 2025/10/3
 */
public interface ISoulItemAbility {

    /**
     * 试图让灵魂物品附身到指定的方块。
     *
     * @param stack 物品堆。
     * @param blockKey 方块的唯一标识符，用于标识需要附身的方块。
     * @param random 随机数生成器，用于在附身过程中引入随机性。
     * @return 是否可以成功附身。
     * @author sxtkl
     * @since 2025/10/7
     */
    boolean possess(ItemStack stack, String blockKey, RandomSource random);
}
