package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import com.iicsadog.blocksblocks.core.network.vo.EmployeeVO;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String HIRE = "gui.blocks_blocks.hiring.hire";
    private static final String FIRE = "gui.blocks_blocks.hiring.fire";
    private static final String TRANSFER = "gui.blocks_blocks.hiring.transfer";

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
            .map(vo -> {
                Map<String, String> params = new HashMap<>();
                String status;
                if (buildingId.equals(vo.workFor())) {
                    status = FIRE;
                } else if (vo.workFor() == null) {
                    status = HIRE;
                } else {
                    status = TRANSFER;
                }
                params.put("name", vo.name());
                params.put("status", status);
                return this.model.expandTemplate(FlowLayout.class, "info", params);
            }).toList();
        rootComponent.childById(FlowLayout.class, "info-container").children(info);
    }

}
