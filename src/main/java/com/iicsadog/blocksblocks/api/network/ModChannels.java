package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheScreen;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import com.iicsadog.blocksblocks.core.network.packet.ActivateSoulNichePacket;
import com.iicsadog.blocksblocks.core.network.packet.OpenSoulNichePacket;
import io.wispforest.owo.network.OwoNetChannel;
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
        NET_CHANNEL.registerServerbound(ActivateSoulNichePacket.class, (message, access) -> {
            ColonyData colony = new ColonyData();
            colony.setId(UUID.randomUUID());
            colony.setName(message.name());
            colony.setOwnerId(access.player().getUUID());
            DataManagers.getInstance(ColonyDataManager::new).addColony(colony);
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
        NET_CHANNEL.registerClientbound(OpenSoulNichePacket.class, (message, access) -> {
            Minecraft.getInstance().setScreen(new SoulNicheScreen());
        });
    }
}
