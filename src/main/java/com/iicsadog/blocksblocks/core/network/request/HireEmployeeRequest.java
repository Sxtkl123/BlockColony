package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import io.wispforest.owo.network.ServerAccess;
import java.util.UUID;

public record HireEmployeeRequest(
    UUID requestId,
    UUID buildingId,
    UUID blockmanId
) implements IRequest<HireEmployeeRequest.Response> {

    @Override
    public Response execute(ServerAccess access) {
        BuildingData building = DataManagers.getInstance(BuildingDataManager::new).query(buildingId);
        if (building == null) {
            return new Response(fail("无法找到建筑物对应的数据。"));
        }
        BlockmanData blockman = DataManagers.getInstance(BlockmanDataManager::new).query(blockmanId);
        if (blockman == null) {
            return new Response(fail("无法找到方块人对应的数据。"));
        }
        blockman.setWorkFor(buildingId);
        DataManagers.getInstance(BlockmanDataManager::new).save(blockman);
        return new Response(success());
    }

    public record Response(
        ResponseInfo responseInfo
    ) implements IResponse {}
}
