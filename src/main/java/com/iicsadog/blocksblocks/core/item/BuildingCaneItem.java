package com.iicsadog.blocksblocks.core.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

/**
 * 建筑手杖物品。
 *
 * @author sxtkl
 * @since 2025/12/3
 */
public class BuildingCaneItem extends Item {
    public BuildingCaneItem() {
        super(new  Item.Properties().stacksTo(1));
    }

    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext context) {
        super.useOn(context);
        // TODO)) 客户端右键打开界面，显示所有客户端的结构
        return InteractionResult.SUCCESS;
    }
}
