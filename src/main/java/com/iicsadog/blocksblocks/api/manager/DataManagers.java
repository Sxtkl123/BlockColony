package com.iicsadog.blocksblocks.api.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * DataManagers 类是一个数据管理器工厂类，负责创建和管理各种数据管理器实例。
 * 它通过单例模式确保每个数据管理器只存在一个实例，并在服务器启动和停止时进行相应的初始化和清理工作。
 *
 * <p>此类使用静态方法来获取数据管理器实例，并确保在服务器未初始化时抛出异常。
 * 数据管理器实例会被缓存在内存中，并在服务器停止时被清除。</p>
 *
 * <p>该类与 {@link AbstractDataManager} 配合使用，后者是所有具体数据管理器的基类，
 * 提供了获取数据管理器名称和工厂方法的标准接口。</p>
 *
 * @author sxtkl
 * @since 2025/10/18
 */
public class DataManagers {

    private static final Map<String, AbstractDataManager> instances = new HashMap<>();
    private static MinecraftServer server;

    /**
     * 在服务器启动时设置服务器实例。
     * 此方法用于初始化数据管理器所需的服务器引用。
     *
     * @param minecraftServer Minecraft服务器实例，用于后续的数据管理操作
     * @author sxtkl
     * @since 2025/10/18
     */
    public static void onServerStart(MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    /**
     * 在服务器停止时调用此方法，用于清理服务器实例和数据管理器实例。
     * 此方法会清除所有已创建的数据管理器实例，并将服务器引用设为null，
     * 以确保在服务器关闭后不会保留任何引用，防止内存泄漏。
     *
     * @author sxtkl
     * @since 2025/10/18
     */
    public static void onServerStop() {
        server = null;
        instances.clear();
    }


    /**
     * 获取指定类型的数据管理器实例。此方法使用单例模式确保每个类型的数据管理器只存在一个实例。
     * 如果实例已存在，则直接返回；否则从数据存储中加载或创建新实例，并将其存储在注册表中。
     *
     * @param supplier 用于创建数据管理器实例的函数式接口，提供数据管理器的构造方法
     * @return 指定类型的数据管理器实例，如果服务器未初始化则抛出运行时异常
     * @author sxtkl
     * @since 2025/10/18
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractDataManager> T getInstance(Supplier<T> supplier) {
        if (server == null) {
            throw new RuntimeException("Server has not been initialized!");
        }

        T temp = supplier.get();
        String name = temp.getManagerName().toString();

        // 如果实例已存在，直接返回
        if (instances.containsKey(name)) {
            return (T) instances.get(name);
        }
        // 否则从数据存储加载或创建新实例，并存储到注册表
        T instance = server.overworld().getDataStorage().computeIfAbsent(
            (SavedData.Factory<? extends T>) temp.getFactory(),
            temp.getManagerName().toString().replace(":", "_")
        );

        instances.put(name, instance);
        return instance;
    }

}
