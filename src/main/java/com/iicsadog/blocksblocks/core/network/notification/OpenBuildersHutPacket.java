package com.iicsadog.blocksblocks.core.network.notification;

import java.util.Optional;
import java.util.UUID;

/**
 * 打开小屋界面通知包。即将废弃。
 *
 * @param colonyId 殖民地id，可能为空
 *
 * @author sxtkl
 * @since 2025/11/18
 */
public record OpenBuildersHutPacket(
    Optional<UUID> colonyId
) {}
