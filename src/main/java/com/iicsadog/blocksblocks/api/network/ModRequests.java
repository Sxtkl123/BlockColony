package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.network.RequestSender;
import com.iicsadog.blocksblocks.core.network.request.CheckHutRequest;
import com.iicsadog.blocksblocks.core.network.request.GetColonyBlockmenRequest;
import com.iicsadog.blocksblocks.core.network.request.GetEmployeesRequest;
import java.util.ArrayList;
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

    public static final List<Class<? extends Record>> REQUESTS = new ArrayList<>();

    public static final List<Class<? extends Record>> RESPONSES = new ArrayList<>();

    static {
        REQUESTS.add(GetColonyBlockmenRequest.class);
        REQUESTS.add(CheckHutRequest.class);
        REQUESTS.add(GetEmployeesRequest.class);

        RESPONSES.add(GetColonyBlockmenRequest.Response.class);
        RESPONSES.add(CheckHutRequest.Response.class);
        RESPONSES.add(GetEmployeesRequest.Response.class);
    }

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

    /**
     * 获取建筑物对应的雇佣视图信息。
     *
     * @param buildingId 建筑物Id
     * @return 视图信息
     * @author sxtkl
     * @since 2025/11/17
     */
    public static RequestSender<GetEmployeesRequest, GetEmployeesRequest.Response> getEmployeesRequest(
        UUID buildingId
    ) {
        return new RequestSender<GetEmployeesRequest, GetEmployeesRequest.Response>()
            .param(new GetEmployeesRequest(UUID.randomUUID(), buildingId));
    }

}
