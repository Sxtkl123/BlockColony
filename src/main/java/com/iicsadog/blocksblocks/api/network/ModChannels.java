package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.gui.screen.BuildersHutMainScreen;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheCreateScreen;
import com.iicsadog.blocksblocks.core.gui.screen.SoulNicheSelectScreen;
import com.iicsadog.blocksblocks.core.manager.client.RequestManager;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import com.iicsadog.blocksblocks.core.network.notification.ActivateSoulNichePacket;
import com.iicsadog.blocksblocks.core.network.notification.OpenBuildersHutPacket;
import com.iicsadog.blocksblocks.core.network.notification.OpenSoulNichePacket;
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
        // 服务端激活魂龛，创立殖民地
        NET_CHANNEL.registerServerbound(ActivateSoulNichePacket.class, (message, access) -> {
            ColonyData colony = new ColonyData();
            colony.setId(UUID.randomUUID());
            colony.setName(message.name());
            colony.setOwnerId(access.player().getUUID());
            DataManagers.getInstance(ColonyDataManager::new).save(colony);
        });

        NET_CHANNEL.addEndecs(builder -> builder.register(ResponseInfo.ENDEC, ResponseInfo.class));
        registerRequests();
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

        registerResponse();
    }

    private static void registerRequests() {
        ModRequests.REQUESTS.forEach(req ->
            NET_CHANNEL.registerServerbound(req, (message, access) ->
                NET_CHANNEL.serverHandle(access.player()).send(((IRequest<?>) message).execute(access))
            )
        );
    }

    private static void registerResponse() {
        ModRequests.RESPONSES.forEach(req ->
            NET_CHANNEL.registerClientbound(req, (message, access)
                -> RequestManager.getInstance().execute((IResponse) message))
        );
    }
}
