package com.iicsadog.blocksblocks.api.manager;


import com.iicsadog.blocksblocks.api.data.IData;
import com.iicsadog.blocksblocks.core.util.BbNbtUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public abstract class AbstractDataManager<D extends IData<D>> extends SavedData {

    protected Map<UUID, D> data = new HashMap<>();

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
     * @return 加载后的AbstractDataManager实例
     * @author sxtkl
     * @since 2025/10/18
     */
    protected AbstractDataManager<D> load(CompoundTag tag) {
        this.data = BbNbtUtils.loadMapData(this.getManagerName().getPath(), tag, this.createData());
        return this;
    }

    /**
     * 创建一个基础的数据，通常情况下传入IData::new()即可。
     *
     * @return 创建数据的方法
     * @author sxtkl
     * @since 2025/11/7
     */
    @NotNull
    protected abstract Supplier<D> createData();

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

    @NotNull
    @Override
    public CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull HolderLookup.Provider provider) {
        BbNbtUtils.saveMapData(this.getManagerName().getPath(), compoundTag, data);
        return compoundTag;
    }

    /**
     * 插入或更新一条数据。
     *
     * @param data 数据
     * @return 是否为插入，true为插入，false为更新
     * @author sxtkl
     * @since 2025/11/7
     */
    public boolean save(D data) {
        boolean result = this.data.put(data.getId(), data) == null;
        this.setDirty();
        return result;
    }

    /**
     * 删除一条数据。
     *
     * @param id 数据id
     * @return 是否成功删除
     * @author sxtkl
     * @since 2025/11/7
     */
    public boolean delete(UUID id) {
        boolean result = this.data.remove(id) != null;
        this.setDirty();
        return result;
    }

    /**
     * 查询一条数据，数据类型为对应的数据类型。
     *
     * @param id 数据id
     * @return 查询到的数据，可能为空
     * @author sxtkl
     * @since 2025/11/7
     */
    @Nullable
    public D query(UUID id) {
        return this.data.getOrDefault(id, null);
    }
}
