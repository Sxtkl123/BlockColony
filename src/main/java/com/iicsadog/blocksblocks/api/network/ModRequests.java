package com.iicsadog.blocksblocks.api.network;

import com.iicsadog.blocksblocks.core.manager.client.RequestManager;
import java.util.List;
import java.util.function.Consumer;

/**
 * ModRequests 类提供了一种便捷的方式来构建和发送网络请求。
 * 它使用构建器模式，允许用户设置请求参数、成功回调和失败回调。
 *
 * @author sxtkl
 * @since 2025/10/30
 */
public class ModRequests {

    public static final List<Class<? extends Record>> REQUESTS = List.of(

    );

    public static final List<Class<? extends Record>> RESPONSES = List.of(

    );

    /**
     * RequestSender 类是一个构建器模式的请求发送器，用于构建和发送网络请求。
     * 它允许用户设置请求参数、成功回调和失败回调，并通过链式调用进行配置。
     *
     * @param <P> 请求参数类型，必须同时实现 Record 和 IRequest 接口
     * @param <R> 响应类型，必须实现 IResponse 接口
     * @author sxtkl
     * @since 2025/10/30
     */
    public static class RequestSender<P extends Record & IRequest<R>, R extends Record & IResponse> {

        private P param;

        private Consumer<R> onSuccess;

        private Consumer<String> onFail;

        /**
         * 设置请求成功时的回调函数。
         *
         * @param onSuccess 当请求成功时执行的回调函数，接收响应数据作为参数
         * @return 返回当前RequestSender实例，支持链式调用
         * @author sxtkl
         * @since 2025/10/30
         */
        public RequestSender<P, R> success(Consumer<R> onSuccess) {
            this.onSuccess = onSuccess;
            return this;
        }

        /**
         * 设置请求失败时的回调函数。
         *
         * @param onFail 当请求失败时执行的回调函数，接收错误信息作为参数
         * @return 返回当前RequestSender实例，支持链式调用
         * @author sxtkl
         * @since 2025/10/30
         */
        public RequestSender<P, R> fail(Consumer<String> onFail) {
            this.onFail = onFail;
            return this;
        }

        /**
         * 设置请求参数。
         *
         * @param param 请求参数对象，必须同时实现 Record 和 IRequest 接口
         * @return 返回当前RequestSender实例，支持链式调用
         * @author sxtkl
         * @since 2025/10/30
         */
        public RequestSender<P, R> param(P param) {
            this.param = param;
            return this;
        }

        /**
         * 发送网络请求的方法。
         * 此方法通过RequestManager的单例实例来发送请求，使用之前设置的参数、成功回调和失败回调。
         * 方法内部调用RequestManager的send方法，将参数和回调传递给请求管理器。
         *
         * @author sxtkl
         * @since 2025/10/30
         */
        public void send() {
            RequestManager.getInstance().send(param, onSuccess, onFail);
        }
    }

}
