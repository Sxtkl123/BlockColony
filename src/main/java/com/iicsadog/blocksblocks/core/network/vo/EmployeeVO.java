package com.iicsadog.blocksblocks.core.network.vo;

import io.wispforest.endec.annotations.NullableComponent;
import java.util.UUID;

/**
 * 记录员工的视图类。
 *
 * @param blockmanId 方块人Id
 * @param name 方块人名称
 * @param workFor 方块人工作的建筑物，可能为空
 *
 * @author sxtkl
 * @since 2025/11/17
 */
public record EmployeeVO(
    UUID blockmanId,
    String name,
    @NullableComponent
    UUID workFor
) {
}
