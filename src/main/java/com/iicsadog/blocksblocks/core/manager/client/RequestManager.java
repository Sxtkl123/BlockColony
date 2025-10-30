package com.iicsadog.blocksblocks.core.manager.client;

import com.iicsadog.blocksblocks.api.network.IRequest;
import com.iicsadog.blocksblocks.api.network.IResponse;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * RequestManager 类负责管理客户端的网络请求和响应处理。
 * 它使用单例模式，维护一个请求映射表，将请求ID与成功和失败回调函数关联起来。
 * 当收到响应时，根据请求ID查找对应的回调函数，并根据响应的成功状态执行相应的回调。
 *
 * @author sxtkl
 * @since 2025/10/30
 */
@OnlyIn(Dist.CLIENT)
public class RequestManager {

    private static RequestManager instance;

    private final Map<UUID, Pair<Consumer<IResponse>, Consumer<String>>> requests;

    private RequestManager() {
        requests = new HashMap<>();
    }

    /**
     * 获取RequestManager的单例实例。
     * 如果实例不存在，则创建一个新的实例。
     *
     * @return RequestManager的单例实例
     * @author sxtkl
     * @since 2025/10/30
     */
    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    /**
     * 执行响应处理，根据请求ID查找对应的回调函数，并根据响应的成功状态执行相应的回调。
     *
     * @param response 收到的响应对象，包含请求ID和响应信息
     * @author sxtkl
     * @since 2025/10/30
     */
    public void execute(IResponse response) {
        Pair<Consumer<IResponse>, Consumer<String>> consumers = requests.get(response.responseInfo().getRequestId());
        requests.remove(response.responseInfo().getRequestId());
        if (consumers == null) {
            return;
        }
        if (response.responseInfo().isSuccess()) {
            if (consumers.getFirst() != null) {
                consumers.getFirst().accept(response);
            }
        } else {
            if (consumers.getSecond() != null) {
                consumers.getSecond().accept(response.responseInfo().getMessage());
            }
        }
    }

    /**
     * 发送网络请求的方法。
     *
     * @param <P> 请求参数类型，必须同时实现Record和IRequest接口
     * @param <R> 响应类型，必须实现IResponse接口
     * @param param 请求参数对象，包含请求ID和请求信息
     * @param onSuccess 请求成功时的回调函数，接收响应对象
     * @param onFail 请求失败时的回调函数，接收错误信息字符串
     * @author sxtkl
     * @since 2025/10/30
     */
    public <P extends Record & IRequest<R>, R extends Record & IResponse> void send(P param, Consumer<R> onSuccess, Consumer<String> onFail) {
        Consumer<IResponse> wrappedConsumer = response -> {
            @SuppressWarnings("unchecked")
            R typedResponse = (R) response;
            onSuccess.accept(typedResponse);
        };

        this.requests.put(param.requestId(), new Pair<>(wrappedConsumer, onFail));
        ModChannels.NET_CHANNEL.clientHandle().send(param);
    }
}