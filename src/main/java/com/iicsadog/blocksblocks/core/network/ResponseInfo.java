package com.iicsadog.blocksblocks.core.network;

import com.iicsadog.blocksblocks.api.network.IRequest;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import java.util.UUID;

/**
 * ResponseInfo 类用于封装网络请求的响应信息，包括请求ID、成功状态和消息。
 * 该类提供了创建成功和失败响应的静态方法，以及获取响应信息的访问器方法。
 *
 * @author sxtkl
 * @since 2025/10/30
 */
public class ResponseInfo {

    public static final StructEndec<ResponseInfo> ENDEC = StructEndecBuilder.of(
        BuiltInEndecs.UUID.fieldOf("requestId", ResponseInfo::getRequestId),
        Endec.BOOLEAN.fieldOf("success", ResponseInfo::isSuccess),
        Endec.STRING.nullableOf().fieldOf("message", ResponseInfo::getMessage),
        ResponseInfo::new
    );

    private final UUID requestId;

    private final boolean success;

    private final String message;

    private ResponseInfo(UUID requestId, boolean success, String message) {
        this.requestId = requestId;
        this.success = success;
        this.message = message;
    }

    /**
     * 创建一个表示成功的响应信息对象。
     * 该方法会使用传入的请求对象中的请求ID，创建一个成功状态为true、消息为null的响应信息。
     *
     * @param request 包含请求信息的对象，用于获取请求ID
     * @return 包含请求ID、成功状态为true、消息为null的响应信息对象
     * @author sxtkl
     * @since 2025/10/30
     */
    public static ResponseInfo success(IRequest<?> request) {
        return new ResponseInfo(request.requestId(), true, null);
    }

    /**
     * 创建一个表示请求失败的响应信息对象。
     * 该响应信息包含请求的ID，并将成功状态设置为false，消息为指定的错误信息。
     *
     * @param request 包含请求信息的对象，用于获取请求ID
     * @param message 失败原因的描述信息
     * @return 包含请求ID和错误消息的失败响应信息对象
     * @author sxtkl
     * @since 2025/10/30
     */
    public static ResponseInfo fail(IRequest<?> request, String message) {
        return new ResponseInfo(request.requestId(), false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UUID getRequestId() {
        return requestId;
    }
}
