package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.gui.component.SlotButtonComponent;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.UUID;
import net.minecraft.client.Minecraft;

/**
 * 伐木工小屋主界面。
 *
 * @author sxtkl
 * @since 2025/11/17
 */
public class LumberjackHutMainScreen extends BaseUIModelScreen<FlowLayout> {
    private final UUID buildingId;

    /**
     * 伐木工小屋主界面。
     *
     * @param buildingId 建筑Id
     * @author sxtkl
     * @since 2025/11/17
     */
    public LumberjackHutMainScreen(UUID buildingId) {
        super(
            FlowLayout.class,
            DataSource.asset(BlocksBlocks.namespace("lumberjack_hut_main"))
        );
        this.buildingId = buildingId;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(SlotButtonComponent.class, "hiring-slot")
            .onPress(btn -> Minecraft.getInstance().setScreen(new HiringScreen(this.buildingId)));
    }
}
