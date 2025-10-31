package com.iicsadog.blocksblocks.core.network.notification;

import java.util.Optional;
import java.util.UUID;

public record OpenBuildersHutPacket(
    Optional<UUID> colonyId
) {
}
