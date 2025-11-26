package com.iicsadog.blocksblocks.core.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record TreeInfo(
    Set<BlockPos> root,
    Set<BlockPos> trunk,
    Block log
) {

    public static final Codec<TreeInfo> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BlockPos.CODEC.listOf().xmap(
                Set::copyOf,
                list -> list.stream().toList()
            ).fieldOf("root").forGetter(TreeInfo::root),
            BlockPos.CODEC.listOf().xmap(
                Set::copyOf,
                list -> list.stream().toList()
            ).fieldOf("trunk").forGetter(TreeInfo::trunk),
            ResourceLocation.CODEC.fieldOf("log").xmap(
                BuiltInRegistries.BLOCK::get,
                BuiltInRegistries.BLOCK::getKey
            ).forGetter(TreeInfo::log)
        ).apply(instance, TreeInfo::new)
    );

    public CompoundTag toNBT() {
        return (CompoundTag) CODEC.encodeStart(NbtOps.INSTANCE, this).result().orElse(new CompoundTag());
    }

    public static TreeInfo fromNBT(CompoundTag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(null);
    }
}
