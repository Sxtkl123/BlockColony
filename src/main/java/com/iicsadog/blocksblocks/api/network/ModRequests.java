package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.network.RequestSender;
import com.iicsadog.blocksblocks.core.network.request.CheckHutRequest;
import com.iicsadog.blocksblocks.core.network.request.FireEmployeeRequest;
import com.iicsadog.blocksblocks.core.network.request.GetColonyBlockmenRequest;
import com.iicsadog.blocksblocks.core.network.request.GetEmployeesRequest;
import com.iicsadog.blocksblocks.core.network.request.HireEmployeeRequest;
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
        REQUESTS.add(HireEmployeeRequest.class);
        REQUESTS.add(FireEmployeeRequest.class);

        RESPONSES.add(GetColonyBlockmenRequest.Response.class);
        RESPONSES.add(CheckHutRequest.Response.class);
        RESPONSES.add(GetEmployeesRequest.Response.class);
        RESPONSES.add(HireEmployeeRequest.Response.class);
        RESPONSES.add(FireEmployeeRequest.Response.class);
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
        return RequestSender.of(new GetEmployeesRequest(UUID.randomUUID(), buildingId));
    }

    /**
     * 雇佣一个方块人。
     *
     * @param buildingId 将其雇佣的建筑物Id
     * @param employeeId 方块人Id
     * @return 是否成功
     * @author sxtkl
     * @since 2025/11/18
     */
    public static RequestSender<HireEmployeeRequest, HireEmployeeRequest.Response> hireEmployee(
        UUID buildingId,
        UUID employeeId
    ) {
        return RequestSender.of(new HireEmployeeRequest(UUID.randomUUID(), buildingId, employeeId));
    }

    /**
     * 解雇一个方块人。
     *
     * @param employeeId 方块人Id
     * @return 是否成功
     * @author sxtkl
     * @since 2025/11/18
     */
    public static RequestSender<FireEmployeeRequest, FireEmployeeRequest.Response> fireEmployee(
        UUID employeeId
    ) {
        return RequestSender.of(new FireEmployeeRequest(UUID.randomUUID(), employeeId));
    }

}
