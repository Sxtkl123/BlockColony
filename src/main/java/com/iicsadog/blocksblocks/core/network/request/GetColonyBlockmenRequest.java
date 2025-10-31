package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import io.wispforest.owo.network.ServerAccess;
import java.util.List;
import java.util.UUID;

/**
 * GetColonyBlockmenRequest 类是一个请求类，用于获取殖民地方块人的信息。
 * 该类实现了 IRequest 接口，并通过 record 实现了不可变的数据结构。
 * 包含请求ID和殖民地ID两个属性，并提供了执行请求的方法。
 * 该请求主要用于从服务器获取指定殖民地的所有方块人信息，
 * 并将数据转换为视图对象（VO）返回给客户端。
 *
 * @author sxtkl
 * @since 2025/10/31
 */
public record GetColonyBlockmenRequest(
    UUID requestId,
    UUID colonyId
) implements IRequest<GetColonyBlockmenRequest.Response> {

    @Override
    public Response execute(ServerAccess access) {
        List<BlockmanData> blockmen = DataManagers.getInstance(BlockmanDataManager::new).getColonyBlockmen(colonyId);
        List<BlockmenVO> vos = blockmen.stream()
            .map(BlockmenVO::fromBlockmanData)
            .toList();
        return new Response(vos, success());
    }

    /**
     * Response 类是一个不可变的数据类，用于表示获取殖民地方块人请求的响应。
     * 该类通过 record 实现，实现了 IResponse 接口，包含方块人列表和响应信息。
     * 主要用于在网络传输中返回殖民地方块人的相关信息。
     *
     * @author sxtkl
     * @since 2025/10/31
     */
    public record Response(
        List<BlockmenVO> blockmen,
        ResponseInfo responseInfo
    ) implements IResponse {}
}
