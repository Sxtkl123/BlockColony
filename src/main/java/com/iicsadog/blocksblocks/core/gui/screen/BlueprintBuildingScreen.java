package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.manager.element.BlueprintElement;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;

public class BlueprintBuildingScreen extends BaseUIModelScreen<FlowLayout> {

    private final BlueprintElement style;

    public BlueprintBuildingScreen(BlueprintElement style) {
        super(
            FlowLayout.class,
            DataSource.asset(BlocksBlocks.namespace("blueprint_building"))
        );
        this.style = style;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
    }
}
