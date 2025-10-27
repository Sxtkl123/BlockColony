package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.BlockmanData;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.gui.screen.BuildersHutMainScreen;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheBlockmenScreen;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheCreateScreen;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheSelectScreen;
import com.iicsadog.blocksblocks.core.manager.data.BlockmanDataManager;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import com.iicsadog.blocksblocks.core.network.packet.ActivateSoulNichePacket;
import com.iicsadog.blocksblocks.core.network.packet.OpenBuildersHutPacket;
import com.iicsadog.blocksblocks.core.network.packet.OpenSoulNichePacket;
import com.iicsadog.blocksblocks.core.network.packet.request.client.GetColonyBlockmenC2S;
import com.iicsadog.blocksblocks.core.network.packet.request.server.GetColonyBlockmenS2C;
import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import io.wispforest.owo.network.OwoNetChannel;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

/**
 * ModChannels 类负责处理模组中的网络通信通道，包括服务器端和客户端的数据包注册与处理。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class ModChannels {

    public static final OwoNetChannel NET_CHANNEL =
        OwoNetChannel.create(ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "net_channel"));

    /**
     * 服务器初始化方法，用于注册服务器端接收到的网络数据包处理器。
     * 当接收到ActivateSoulNichePacket类型的网络数据包时，会创建一个新的殖民地数据对象，
     * 设置其ID、名称和所有者ID，并将该殖民地添加到殖民地数据管理器中。
     *
     * @author sxtkl
     * @since 2025/10/15
     */
    public static void onServerInit() {
        // TODO)) 这里和下面所有的网络包的实现都挤在一起了，很不优雅，有机会抽象一下
        // 服务端激活魂龛，创立殖民地
        NET_CHANNEL.registerServerbound(ActivateSoulNichePacket.class, (message, access) -> {
            ColonyData colony = new ColonyData();
            colony.setId(UUID.randomUUID());
            colony.setName(message.name());
            colony.setOwnerId(access.player().getUUID());
            DataManagers.getInstance(ColonyDataManager::new).addColony(colony);
        });

        // 服务端回复：获得某一殖民地的所有方块人信息
        NET_CHANNEL.registerServerbound(GetColonyBlockmenC2S.class, (message, access) -> {
            List<BlockmanData> blockmen = DataManagers.getInstance(BlockmanDataManager::new).getColonyBlockmen(message.colonyId());
            List<BlockmenVO> vos = blockmen.stream()
                .map(BlockmenVO::fromBlockmanData)
                .toList();
            NET_CHANNEL.serverHandle(access.player()).send(new GetColonyBlockmenS2C(vos));
        });
    }

    /**
     * 客户端初始化方法，用于注册客户端接收到的网络数据包处理器。
     * 当接收到OpenSoulNichePacket类型的网络数据包时，会打开灵魂壁龛界面。
     *
     * @author sxtkl
     * @since 2025/10/15
     */
    public static void onClientInit() {
        // 客户端打开魂龛
        NET_CHANNEL.registerClientbound(OpenSoulNichePacket.class, (message, access) -> {
            if (message.create()) {
                Minecraft.getInstance().setScreen(new SoulNicheCreateScreen());
                return;
            }
            if (message.colonyId().isPresent()) {
                Minecraft.getInstance().setScreen(new SoulNicheSelectScreen(message.colonyId().get()));
            }
        });
        // 客户端打开建筑工小屋
        NET_CHANNEL.registerClientbound(OpenBuildersHutPacket.class, (message, access) -> {
            if (message.colonyId().isPresent()) {
                Minecraft.getInstance().setScreen(new BuildersHutMainScreen(message.colonyId().get()));
            }
        });

        // 客户端发送：获得某一殖民地的所有方块人信息
        NET_CHANNEL.registerClientbound(GetColonyBlockmenS2C.class, (message, access) -> {
            if (!(Minecraft.getInstance().screen instanceof SoulNicheBlockmenScreen screen)) {
                return;
            }
            screen.setVos(message.blockmen());
        });
    }
}
