package com.iicsadog.blocksblocks.core.network.request;

import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import io.wispforest.endec.annotations.NullableComponent;
import io.wispforest.owo.network.ServerAccess;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * 检查一个方块是否是具有id的小屋方块，即被玩家亲自放置的方块。
 *
 * @param pos 方块位置
 * @author sxtkl
 * @since 2025/11/8
 */
public record CheckHutRequest(
    UUID requestId,
    BlockPos pos
) implements IRequest<CheckHutRequest.Response> {

    @Override
    public Response execute(ServerAccess access) {
        // 这里的Level取自玩家所在的level，而非由外部传参。
        Level level = access.player().level();
        if (!(level.getBlockEntity(pos) instanceof BaseHutBlockEntity hutEntity)) {
            return new Response(null, fail("该方块不为小屋实体。"));
        }
        if (hutEntity.getBuildingId() == null) {
            return new Response(null, fail("无法获得小屋方块对应的id。"));
        }
        return new Response(hutEntity.getBuildingId(), success());
    }

    /**
     * 请求回复体，是否成功均取决于responseInfo。
     *
     * @param responseInfo 成功或失败信息
     * @author sxtkl
     * @since 2025/11/8
     */
    public record Response(
        @NullableComponent
        UUID buildingId,
        ResponseInfo responseInfo
    ) implements IResponse {}
}
