package com.iicsadog.blocksblocks.api.manager;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * AbstractDataManager 是一个抽象基类，继承自 SavedData，用于实现数据管理器的核心功能。
 * 该类提供了获取数据管理器工厂和名称的标准接口，由具体的数据管理器实现类提供这些方法的实现。
 *
 * <p>此类作为所有具体数据管理器的基类，确保了数据管理器的一致性和可扩展性。
 * 通过抽象方法 getFactory() 和 getManagerName()，子类必须提供自己的工厂方法和名称标识。</p>
 *
 * <p>此类与 DataManagers 类配合使用，DataManagers 负责创建和管理数据管理器实例，
 * 并通过单例模式确保每个类型的数据管理器只存在一个实例。</p>
 *
 * @author sxtkl
 * @since 2025/10/18
 */
public abstract class AbstractDataManager extends SavedData {

    /**
     * AbstractDataManager 是一个抽象基类，继承自 SavedData，用于实现数据管理器的核心功能。
     * 该类提供了获取数据管理器工厂和名称的标准接口，由具体的数据管理器实现类提供这些方法的实现。
     *
     * <p>此类作为所有具体数据管理器的基类，确保了数据管理器的一致性和可扩展性。
     * 通过抽象方法 getFactory() 和 getManagerName()，子类必须提供自己的工厂方法和名称标识。</p>
     *
     * <p>此类与 DataManagers 类配合使用，DataManagers 负责创建和管理数据管理器实例，
     * 并通过单例模式确保每个类型的数据管理器只存在一个实例。</p>
     *
     * @author sxtkl
     * @since 2025/10/18
     */
    protected AbstractDataManager() {}

    /**
     * 从标签和提供者加载抽象数据管理器实例。
     * 此方法是一个抽象方法，需要在子类中实现，用于从CompoundTag中恢复数据管理器的状态。
     *
     * @param tag 包含数据管理器持久化数据的CompoundTag对象
     * @param provider 提供注册表查找功能的HolderLookup.Provider对象
     * @return 加载后的AbstractDataManager实例
     * @author sxtkl
     * @since 2025/10/18
     */
    protected abstract AbstractDataManager load(CompoundTag tag, HolderLookup.Provider provider);

    /**
     * 获取数据管理器的名称标识符。
     * 此方法由子类实现，返回一个ResourceLocation类型的名称，用于唯一标识该数据管理器。
     * 该名称在DataManagers类中用于缓存和检索数据管理器实例，确保每个类型的数据管理器只存在一个实例。
     *
     * @return 数据管理器的名称标识符，类型为ResourceLocation
     * @author sxtkl
     * @since 2025/10/18
     */
    protected abstract ResourceLocation getManagerName();

}
