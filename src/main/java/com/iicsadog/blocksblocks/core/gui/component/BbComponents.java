package com.iicsadog.blocksblocks.core.gui.component;

import com.iicsadog.blocksblocks.BlocksBlocks;
import io.wispforest.owo.ui.parsing.UIParsing;
import net.minecraft.world.item.ItemStack;

/**
 * 提供UI组件的工厂类，用于创建和注册各种UI组件。
 *
 * @author sxtkl
 * @since 2025/10/24
 */
public class BbComponents {

    /**
     * 创建一个带有物品的按钮组件。
     *
     * @param item 要显示在按钮中的物品堆栈
     * @return 创建的SlotButtonComponent实例
     * @author sxtkl
     * @since 2025/10/24
     */
    public static SlotButtonComponent slotButton(ItemStack item) {
        return new SlotButtonComponent(item);
    }

    /**
     * 注册UI组件的方法，将自定义的UI组件注册到UI解析系统中。
     *
     * @author sxtkl
     * @since 2025/10/24
     */
    public static void registerComponents() {
        UIParsing.registerFactory(BlocksBlocks.namespace("slot-button"), ele -> slotButton(ItemStack.EMPTY));
    }

}
