package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.network.ResponseInfo;
import io.wispforest.owo.network.ServerAccess;
import java.util.UUID;

/**
 * IRequest 接口定义了网络请求的基本结构和行为。
 * 它是一个泛型接口，用于处理请求和响应的交互。
 *
 * @param <R> 响应类型，必须实现 IResponse 接口
 * @author sxtkl
 * @since 2025/10/30
 */
public interface IRequest<R extends Record & IResponse> {

    /**
     * 返回一个表示请求成功的响应信息对象。
     * 该响应信息包含请求的ID，并将成功状态设置为true，消息为null。
     *
     * @return 包含请求ID的成功响应信息对象
     * @author sxtkl
     * @since 2025/10/30
     */
    default ResponseInfo success() {
        return ResponseInfo.success(this);
    }

    /**
     * 返回一个表示请求失败的响应信息对象。
     * 该响应信息包含请求的ID，并将成功状态设置为false，消息为指定的错误信息。
     *
     * @param message 失败原因的描述信息
     * @return 包含请求ID和错误消息的失败响应信息对象
     * @author sxtkl
     * @since 2025/10/30
     */
    default ResponseInfo fail(String message) {
        return ResponseInfo.fail(this, message);
    }

    /**
     * 执行请求并返回响应结果。
     *
     * @param access 服务器访问对象，用于获取玩家信息和发送响应
     * @return 执行请求后返回的响应对象
     * @author sxtkl
     * @since 2025/10/30
     */
    R execute(ServerAccess access);

    /**
     * 获取请求的唯一标识符。
     *
     * @return 表示请求唯一标识的UUID对象
     * @author sxtkl
     * @since 2025/10/30
     */
    UUID requestId();
}
