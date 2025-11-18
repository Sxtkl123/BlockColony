package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import com.iicsadog.blocksblocks.core.network.vo.EmployeeVO;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 雇佣界面。
 *
 * @author sxtkl
 * @since 2025/11/17
 */
public class HiringScreen extends BaseUIModelScreen<FlowLayout> {
    private final List<EmployeeVO> vos = new ArrayList<>();
    private final UUID buildingId;

    @Override
    protected void init() {
        super.init();
        ModRequests.getEmployeesRequest(this.buildingId)
            .success(res -> {
                this.vos.clear();
                vos.addAll(res.employees());
                this.build(this.uiAdapter.rootComponent);
            })
            .fail(BlocksBlocks.LOGGER::error)
            .send();
    }

    /**
     * 雇佣界面。
     *
     * @param buildingId 建筑Id
     * @author sxtkl
     * @since 2025/11/17
     */
    public HiringScreen(UUID buildingId) {
        super(
            FlowLayout.class,
            DataSource.asset(BlocksBlocks.namespace("hiring"))
        );
        this.buildingId = buildingId;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        List<FlowLayout> info = this.vos.stream()
            .map(vo -> this.model.expandTemplate(FlowLayout.class, "info", Map.of(
                "name", vo.name(),
                "status", "Hire"
            ))).toList();
        rootComponent.childById(FlowLayout.class, "info-container").children(info);
    }

}
