package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.network.ResponseInfo;

/**
 * IResponse 接口定义了网络响应的基本结构和行为。
 * 它是一个泛型接口，用于处理请求和响应的交互。
 * 所有响应类型都必须实现此接口，以确保它们能够正确地与请求系统交互。
 *
 * @author sxtkl
 * @since 2025/10/30
 */
public interface IResponse {

    /**
     * 获取响应信息对象，该对象包含请求的执行结果状态和相关信息。
     *
     * @return 包含请求ID、成功状态和消息的响应信息对象
     * @author sxtkl
     * @since 2025/10/30
     */
    ResponseInfo responseInfo();
}
