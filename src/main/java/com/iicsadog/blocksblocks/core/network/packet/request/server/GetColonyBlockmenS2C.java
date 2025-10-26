package com.iicsadog.blocksblocks.core.network.packet.request.server;

import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import java.util.List;

/**
 * GetColonyBlockmenS2C 是一个网络数据包类，用于从服务器向客户端发送殖民地的所有方块人信息。
 * 该类通过 record 实现，包含一个 blockmen 属性，用于存储方块人的视图对象列表。
 * 主要用于在网络传输中传递殖民地的方块人信息，供客户端显示使用。
 *
 * @author sxtkl
 * @since 2025/10/26
 */
public record GetColonyBlockmenS2C(
    List<BlockmenVO> blockmen
) {}
