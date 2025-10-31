package com.iicsadog.blocksblocks.core.network.notification;

/**
 * 激活灵魂壁龛的网络数据包类。
 * 当客户端发送此数据包到服务器时，请求创建一个新的殖民地。
 * 服务器接收到此数据包后，会创建一个新的殖民地数据对象，
 * 设置其ID、名称和所有者ID，并将该殖民地添加到殖民地数据管理器中。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public record ActivateSoulNichePacket(
    String name
) {}
