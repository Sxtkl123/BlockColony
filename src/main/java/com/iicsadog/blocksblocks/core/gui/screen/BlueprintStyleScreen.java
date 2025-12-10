package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.manager.common.BlueprintManager;
import com.iicsadog.blocksblocks.core.manager.element.BlueprintElement;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlueprintStyleScreen extends BaseUIModelScreen<FlowLayout> {
    private final List<BlueprintElement> styles;

    public BlueprintStyleScreen() {
        super(
            FlowLayout.class,
            DataSource.asset(BlocksBlocks.namespace("blueprint_style"))
        );
        this.styles = new ArrayList<>(BlueprintManager.getInstance().getBlueprints().values());
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        for (BlueprintElement style : styles) {
            Map<String, String> params = Map.of(
                "name", style.getName(),
                "description", style.getDescription()
            );
            FlowLayout stylePack = this.model.expandTemplate(FlowLayout.class, "style-pack", params);
            rootComponent.childById(FlowLayout.class, "style-pack-container").child(stylePack);
        }
    }
}
