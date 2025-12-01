package com.iicsadog.blocksblocks.api.data;

import com.mojang.serialization.Codec;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import org.jetbrains.annotations.Nullable;

/**
 * 可以使用Codec编码的数据。
 *
 * @author sxtkl
 * @since 2025/12/1
 */
public interface ICodecData<T extends ICodecData<T>> {

    /**
     * 利用Codec编码保存。
     *
     * @param codec Codec编码
     * @return 保存后的tag
     * @author sxtkl
     * @since 2025/12/1
     */
    @SuppressWarnings("unchecked")
    @Nullable
    default CompoundTag save(Codec<T> codec) {
        return (CompoundTag) codec.encodeStart(NbtOps.INSTANCE, (T) this).result().orElse(null);
    }

    /**
     * 利用codec读取。
     *
     * @param codec Codec编码
     * @param tag 标签
     * @return 读取后的实体类
     * @author sxtkl
     * @since 2025/12/1
     */
    @Nullable
    static <T> T load(Codec<T> codec, CompoundTag tag) {
        return codec.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }

    /**
     * 设置Id。
     *
     * @param id Id
     * @author sxtkl
     * @since 2025/12/1
     */
    void setId(UUID id);

    /**
     * 获取Id。
     *
     * @return 获取到的Id。
     * @author sxtkl
     * @since 2025/12/1
     */
    UUID getId();
}
