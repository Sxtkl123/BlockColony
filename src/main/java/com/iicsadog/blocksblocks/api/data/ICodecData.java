package com.iicsadog.blocksblocks.api.data;

import com.mojang.serialization.Codec;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import org.jetbrains.annotations.Nullable;

public interface ICodecData<T extends ICodecData<T>> {

    @SuppressWarnings("unchecked")
    @Nullable
    default CompoundTag save(Codec<T> codec) {
        return (CompoundTag) codec.encodeStart(NbtOps.INSTANCE, (T) this).result().orElse(null);
    }

    @Nullable
    static <T> T load(Codec<T> codec, CompoundTag tag) {
        return codec.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }

    void setId(UUID id);

    UUID getId();
}
