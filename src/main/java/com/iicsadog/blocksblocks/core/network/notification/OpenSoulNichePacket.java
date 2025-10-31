package com.iicsadog.blocksblocks.core.network.notification;

import java.util.Optional;
import java.util.UUID;

/**
 * 打开灵魂壁龛界面的网络数据包类。
 * 当服务器接收到此数据包时，会打开客户端的灵魂壁龛界面，允许玩家创建新的殖民地。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public record OpenSoulNichePacket(
    boolean create,
    Optional<UUID> colonyId
) {
}
