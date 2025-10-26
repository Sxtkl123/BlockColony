package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.gui.component.SlotButtonComponent;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

/**
 * 灵魂壁龛灵魂选择界面类，用于选择要进入的下一级菜单。
 *
 * @author sxtkl
 * @since 2025/10/22
 */
public class SoulNicheSelectScreen extends BaseUIModelScreen<FlowLayout> {

    private final UUID colonyId;
    /**
     * 灵魂壁龛灵魂选择界面类，用于选择要进入的下一级菜单。
     *
     * @author sxtkl
     * @since 2025/10/22
     */
    public SoulNicheSelectScreen(UUID colonyId) {
        super(
            FlowLayout.class,
            DataSource.asset(
                ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "soul_niche_select"))
        );
        this.colonyId = colonyId;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(SlotButtonComponent.class, "blockmen-slot")
            .onPress(btn -> Minecraft.getInstance().setScreen(new SoulNicheBlockmenScreen(this.colonyId)));
    }
}
