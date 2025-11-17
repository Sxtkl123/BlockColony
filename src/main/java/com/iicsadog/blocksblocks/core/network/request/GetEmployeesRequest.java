package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import com.iicsadog.blocksblocks.core.network.vo.EmployeeVO;
import io.wispforest.owo.network.ServerAccess;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record GetEmployeesRequest(
    UUID requestId,
    UUID buildingId
) implements IRequest<GetEmployeesRequest.Response> {

    @Override
    public Response execute(ServerAccess access) {
        BuildingData buildingData = DataManagers.getInstance(BuildingDataManager::new).query(buildingId);
        if (Objects.isNull(buildingData)) {
            return new Response(List.of(), fail("无法找到对应的建筑物。"));
        }
        UUID colonyId = buildingData.getColonyId();
        List<BlockmanData> blockmen = DataManagers.getInstance(BlockmanDataManager::new).getColonyBlockmen(colonyId);
        List<EmployeeVO> vos = blockmen.stream().map(
            data -> new EmployeeVO(data.getId(), data.getName(), data.getWorkFor())
        ).toList();
        return new Response(vos, success());
    }

    public record Response(
        List<EmployeeVO> employees,
        ResponseInfo responseInfo
    ) implements IResponse {}
}
