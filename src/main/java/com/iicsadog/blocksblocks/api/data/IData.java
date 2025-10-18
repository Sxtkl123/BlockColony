package com.iicsadog.blocksblocks.api.data;


import net.minecraft.nbt.CompoundTag;

/**
 * IData 接口定义了数据对象的基本操作规范，主要用于数据的保存功能。
 * 实现此接口的类需要提供将自身数据保存到 CompoundTag 中的能力。
 *
 * @author sxtkl
 * @since 2025/10/17
 */
public interface IData {

    /**
     * 将数据保存到指定的 CompoundTag 中。
     *
     * @param tag 用于保存数据的 CompoundTag 对象
     * @return 包含保存数据的 CompoundTag 对象
     * @author sxtkl
     * @since 2025/10/17
     */
    CompoundTag save(CompoundTag tag);

}
