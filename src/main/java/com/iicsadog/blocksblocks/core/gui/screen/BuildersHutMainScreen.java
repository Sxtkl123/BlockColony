package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public class BuildersHutMainScreen extends BaseUIModelScreen<FlowLayout> {
    private final UUID colonyId;

    /**
     * 建筑工小屋主界面，用于显示简单信息并选择要进入的下一级菜单。
     *
     * @author arxyt
     * @since 2025/10/28
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
