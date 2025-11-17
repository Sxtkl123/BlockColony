package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.network.RequestSender;
import com.iicsadog.blocksblocks.core.network.request.CheckHutRequest;
import com.iicsadog.blocksblocks.core.network.request.GetColonyBlockmenRequest;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;

/**
 * ModRequests 类提供了一种便捷的方式来构建和发送网络请求。
 * 它使用构建器模式，允许用户设置请求参数、成功回调和失败回调。
 *
 * @author sxtkl
 * @since 2025/10/30
 */
public class ModRequests {

    public static final List<Class<? extends Record>> REQUESTS = List.of(
        GetColonyBlockmenRequest.class,
        CheckHutRequest.class
    );

    public static final List<Class<? extends Record>> RESPONSES = List.of(
        GetColonyBlockmenRequest.Response.class,
        CheckHutRequest.Response.class
    );

    /**
     * 获取殖民地方块人信息的方法。
     * 此方法创建一个请求发送器，用于获取指定殖民地的所有方块人信息。
     *
     * @param colonyId 殖民地的唯一标识符，用于指定要查询的殖民地
     * @return 返回一个RequestSender实例，用于配置和发送获取殖民地方块人的请求
     * @author sxtkl
     * @since 2025/10/31
     */
    public static RequestSender<GetColonyBlockmenRequest, GetColonyBlockmenRequest.Response> getColonyBlockmen(UUID colonyId) {
        return new RequestSender<GetColonyBlockmenRequest, GetColonyBlockmenRequest.Response>()
            .param(new GetColonyBlockmenRequest(UUID.randomUUID(), colonyId));
    }

    /**
     * 判断一个方块实体是否是小屋方块且具有建筑id。
     *
     * @param pos 位置
     * @return 判断结果
     * @author sxtkl
     * @since 2025/11/17
     */
    public static RequestSender<CheckHutRequest, CheckHutRequest.Response> getCheckHutRequest(
        BlockPos pos
    ) {
        return new RequestSender<CheckHutRequest, CheckHutRequest.Response>()
            .param(new CheckHutRequest(UUID.randomUUID(), pos));
    }

}
