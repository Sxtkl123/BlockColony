package com.iicsadog.blocksblocks.api.data;


import java.util.UUID;
import net.minecraft.nbt.CompoundTag;

/**
 * IData 接口定义了数据对象的基本操作规范，主要用于数据的保存功能。
 * 实现此接口的类需要提供将自身数据保存到 CompoundTag 中的能力。
 *
 * @author sxtkl
 * @since 2025/10/17
 */
public interface IData<T extends IData<T>> {

    /**
     * 将数据保存到指定的 CompoundTag 中。
     *
     * @param tag 用于保存数据的 CompoundTag 对象
     * @return 包含保存数据的 CompoundTag 对象
     * @author sxtkl
     * @since 2025/10/17
     */
    CompoundTag save(CompoundTag tag);

    /**
     * 从 CompoundTag 中加载数据。
     *
     * @param tag 包含要加载的数据的 CompoundTag 对象
     * @return 包含加载的数据的对象
     * @author sxtkl
     * @since 2025/10/18
     */
    T load(CompoundTag tag);

    /**
     * 设置对象的唯一标识符。
     *
     * @param id 用于标识对象的UUID值
     * @author sxtkl
     * @since 2025/10/20
     */
    void setId(UUID id);

    /**
     * 获取对象的唯一标识符。
     *
     * @return 对象的唯一标识符UUID值
     * @author sxtkl
     * @since 2025/10/20
     */
    UUID getId();

}
