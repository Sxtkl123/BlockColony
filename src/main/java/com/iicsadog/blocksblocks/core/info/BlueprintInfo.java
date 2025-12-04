package com.iicsadog.blocksblocks.core.info;

import java.util.List;
import java.util.Optional;

public record BlueprintInfo(
    String name,
    String description,
    String logo,
    List<BuildingInfo> buildings
) {

    public record BuildingInfo(
        String name,
        String structure,
        String icon,
        HutInfo hut
    ) {}

    public record HutInfo(
        int level,
        String type
    ) {}

}
