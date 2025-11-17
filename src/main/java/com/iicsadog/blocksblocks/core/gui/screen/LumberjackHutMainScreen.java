package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.UUID;

/**
 * 伐木工小屋主界面。
 *
 * @author sxtkl
 * @since 2025/11/17
 */
public class LumberjackHutMainScreen extends BaseUIModelScreen<FlowLayout> {
    private final UUID buildingId;

    @Override
    protected void init() {
        super.init();
        ModRequests.getEmployeesRequest(this.buildingId)
            .success(res -> res.employees().forEach((e) -> BlocksBlocks.LOGGER.info(e.toString())))
            .fail(BlocksBlocks.LOGGER::error)
            .send();
    }

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

    }
}
