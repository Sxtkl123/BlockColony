package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;

public class BlueprintStyleScreen extends BaseUIModelScreen<FlowLayout> {
    public BlueprintStyleScreen() {
        super(
            FlowLayout.class,
            DataSource.asset(BlocksBlocks.namespace("blueprint_style"))
        );
    }

    @Override
    protected void build(FlowLayout rootComponent) {

    }
}
