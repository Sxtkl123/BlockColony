# Request 请求系统使用说明

想要使用本项目编写的请求系统，需要遵循四个步骤实现。

这五个步骤分别为：

1. 实现`IRequest`请求接口，创建请求记录，并编写服务器响应逻辑。
2. 实现`IResponse`响应接口，创建响应记录。
3. 注册请求和响应。
4. 调用请求。

下面将会使用一个测试用请求来进行演示。

## 创建请求记录和响应记录

测试类请求会包含3个字段，分别为：
- requestId(此字段是所有请求都必须包含的)
- number1
- number2

```java
// 请求记录
public record TestAddRequest(
    UUID requestId,
    int number1,
    int number2
) implements IRequest<TestAddResponse> {

    @Override
    public TestAddResponse execute(ServerAccess access) {
        log.info("收到客户端信息：{} 和 {}", this.number1, this.number2);
        return new TestAddResponse(success(), number1 + number2);
    }
}
```

注意这里的请求记录必须实现继承自`IRequest`，实现`execute`方法。

```java
// 响应记录
public record TestAddResponse(
    ResponseInfo responseInfo,
    int result
) implements IResponse {
}
```

注意这里的响应记录必须实现继承自`IResponse`。

## 注册

注册请求和响应需要在`ModRequests`类中进行。

```java
public static final List<Class<? extends Record>> REQUESTS = List.of(
    SomeOtherRequest.class, TestAddRequest.class
);

public static final List<Class<? extends Record>> RESPONSES = List.of(
    SomeOtherResponse.class, TestAddResponse.class
);
```

下一步不是必须实现，但是推荐进行：

```java
public static RequestSender<TestAddRequest, TestAddResponse> test(int num1, int num2) {
    return RequestSender.of(new TestAddRequest(UUID.randomUUID(), num1, num2));
}
```

## 调用

在客户端中使用如下代码调用：

```java
public void someMethod() {
    ModRequests.test(1, 2)
        .success(res -> log.info("服务器返回结果：{}", res.result))
        .fail(msg -> log.info("服务器返回错误：{}", msg))
        .send();
}
```
