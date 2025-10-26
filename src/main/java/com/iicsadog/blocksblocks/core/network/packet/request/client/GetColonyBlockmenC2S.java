package com.iicsadog.blocksblocks.core.network.packet.request.client;

import java.util.UUID;


/**
 * 客户端到服务器的网络数据包，用于请求获取特定殖民地的所有方块人信息。
 * 当客户端需要显示某个殖民地的方块人列表时，会发送此数据包给服务器。
 * 服务器接收到此数据包后，会查询对应殖民地的方块人信息，并通过GetColonyBlockmenS2C数据包返回给客户端。
 *
 * @author sxtkl
 * @since 2025/10/26
 */
public record GetColonyBlockmenC2S(
    UUID colonyId
) {}
