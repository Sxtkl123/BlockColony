package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.ModRegistries;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.iicsadog.blocksblocks.core.manager.common.BlockmanEntityCacheManager;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import io.wispforest.owo.network.ServerAccess;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

/**
 * 雇佣一个方块人的请求。
 *
 * @param requestId 请求id
 * @param blockmanId 方块人id
 * @param buildingId 建筑物id
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public record HireEmployeeRequest(
    UUID requestId,
    UUID buildingId,
    UUID blockmanId,
    ResourceLocation job
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
        BlockmanEntityCacheManager.getInstance().getEntity(blockman.getId()).ifPresent(entity -> entity.setJob(ModRegistries.JOB.get(job)));
        return new Response(success());
    }

    /**
     * 请求回复体，仅代表是否成功。
     *
     * @param responseInfo 回复体
     *
     * @author sxtkl
     * @since 2025/11/18
     */
    public record Response(
        ResponseInfo responseInfo
    ) implements IResponse {}
}
