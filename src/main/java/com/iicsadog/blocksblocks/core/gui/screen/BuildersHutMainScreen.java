package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class BuildersHutMainScreen extends BaseUIModelScreen<FlowLayout> {
    private final UUID colonyId;

    /**
     * 灵魂壁龛灵魂选择界面类，用于选择要进入的下一级菜单。
     *
     * @author sxtkl
     * @since 2025/10/22
     */
    public BuildersHutMainScreen(UUID colonyId) {
        super(
                FlowLayout.class,
                DataSource.asset(
                        ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "builders_hut_main"))
        );
        this.colonyId = colonyId;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        // 先置空
    }
}
