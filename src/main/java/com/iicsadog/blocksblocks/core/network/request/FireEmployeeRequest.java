package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.job.ModJobs;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.manager.common.BlockmanEntityCacheManager;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import io.wispforest.owo.network.ServerAccess;
import java.util.UUID;

/**
 * 解雇一个方块人的请求。
 *
 * @param requestId 请求id
 * @param blockmanId 方块人id
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public record FireEmployeeRequest(
    UUID requestId,
    UUID blockmanId
) implements IRequest<FireEmployeeRequest.Response> {

    @Override
    public Response execute(ServerAccess access) {
        BlockmanData blockman = DataManagers.getInstance(BlockmanDataManager::new).query(blockmanId);
        if (blockman == null) {
            return new Response(fail("无法找到方块人对应的数据。"));
        }
        blockman.setWorkFor(null);
        blockman.setJob(ModJobs.EMPTY.getId());
        BlockmanEntityCacheManager.getInstance().getEntity(blockman.getId()).ifPresent(BlockmanEntity::updateBrainByJob);
        DataManagers.getInstance(BlockmanDataManager::new).save(blockman);
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
