package com.iicsadog.blocksblocks.core.network.packet.request.server;

import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import java.util.List;

public record GetColonyBlockmenS2C(
    List<BlockmenVO> blockmen
) {}
