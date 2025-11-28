package com.iicsadog.blocksblocks.core.info;

import com.iicsadog.blocksblocks.core.entity.ai.Task;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * 树木信息。
 *
 * @param root 树根
 * @param trunk 树干
 * @param log 树木方块
 *
 * @author sxtkl
 * @since 2025/11/27
 */
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

    public static final Codec<Task<TreeInfo>> TASK_CODEC = Task.createCodec(CODEC);
}
