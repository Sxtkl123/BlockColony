package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.job.ModJobs;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import com.iicsadog.blocksblocks.core.network.vo.EmployeeVO;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

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
        this.refreshInfos();
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
        List<FlowLayout> infos = new ArrayList<>();
        for (EmployeeVO employeeVO : this.vos) {
            Map<String, String> params = new HashMap<>();
            Consumer<ButtonComponent> onPress;
            String status;
            if (buildingId.equals(employeeVO.workFor())) {
                status = FIRE;
                onPress = evt -> ModRequests.fireEmployee(employeeVO.blockmanId())
                    .success(this::refreshInfos)
                    .fail(BlocksBlocks.LOGGER::error)
                    .send();
            } else if (employeeVO.workFor() == null) {
                status = HIRE;
                onPress = evt -> ModRequests.hireEmployee(buildingId, employeeVO.blockmanId(),
                        ModJobs.LUMBERJACK.getId())
                    .success(this::refreshInfos)
                    .fail(BlocksBlocks.LOGGER::error)
                    .send();
            } else {
                status = TRANSFER;
                onPress = evt -> ModRequests.hireEmployee(buildingId, employeeVO.blockmanId(), ModJobs.LUMBERJACK.getId())
                    .success(this::refreshInfos)
                    .fail(BlocksBlocks.LOGGER::error)
                    .send();
            }
            params.put("name", employeeVO.name());
            params.put("status", status);
            FlowLayout info = this.model.expandTemplate(FlowLayout.class, "info", params);
            info.childById(ButtonComponent.class, "hire-button").onPress(onPress);
            infos.add(info);
        }
        rootComponent.childById(FlowLayout.class, "info-container").clearChildren();
        rootComponent.childById(FlowLayout.class, "info-container").children(infos);
    }

    private void refreshInfos() {
        ModRequests.getEmployeesRequest(buildingId)
            .success(e -> this.refreshInfos(e.employees()))
            .fail(BlocksBlocks.LOGGER::error)
            .send();
    }

    private void refreshInfos(IResponse ignore) {
        refreshInfos();
    }

    private void refreshInfos(List<EmployeeVO> vos) {
        this.vos.clear();
        this.vos.addAll(vos);
        this.build(this.uiAdapter.rootComponent);
    }

}
