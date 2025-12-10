package com.iicsadog.blocksblocks.core.item;

import com.iicsadog.blocksblocks.core.gui.screen.BlueprintStyleScreen;
import net.minecraft.client.Minecraft;
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
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext context) {
        super.useOn(context);
        if (!context.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        Minecraft client = Minecraft.getInstance();
        client.setScreen(new BlueprintStyleScreen());
        return InteractionResult.SUCCESS;
    }
}
