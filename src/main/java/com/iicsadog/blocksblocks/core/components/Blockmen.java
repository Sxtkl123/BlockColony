package com.iicsadog.blocksblocks.core.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * 表示一个 Blockmen 实例的记录类，用于处理特定块的接受和拒绝逻辑。
 * 该类存储了 Blockmen 的唯一标识符 (UUID)，以及被接受和拒绝的块集合。
 *
 * <p>主要功能包括：</p>
 * <ul>
 *   <li>存储 Blockmen 的唯一标识符。</li>
 *   <li>通过集合存储拒绝的块 (rejectedBlocks)。</li>
 *   <li>通过集合存储接受的块 (acceptedBlocks)。</li>
 *   <li>提供编解码器 (CODEC)，支持与序列化相关的功能。</li>
 *   <li>支持流式编解码器 (STREAM_CODEC)，便于字节流通信中的编码和解析操作。</li>
 * </ul>
 *
 * <p><b>重要说明：</b></p>
 * <ul>
 *   <li>集合 rejectedBlocks 和 acceptedBlocks 是不可变集合，将确保只读性。</li>
 *   <li>UUIDUtil.CODEC 和 UUIDUtil.STREAM_CODEC 用于对唯一标识符进行编码或解码。</li>
 *   <li>rejectedBlocks 和 acceptedBlocks 的编解码器将利用 {@code Set<String>} 模型映射提供高效处理。</li>
 * </ul>
 *
 * @author sxt
 * @since 2025/10/07
 */
public record Blockmen(
    UUID id,
    Set<String> rejectedBlocks,
    Set<String> acceptedBlocks
) {

    /**
     * 构造一个新的 {@code Blockmen} 实例，初始化其接受的和拒绝的方块集合。
     *
     * @param rejectedBlocks 初始化的拒绝方块集合。
     * @param acceptedBlocks 初始化的接受方块集合。
     * @author sxt
     * @since 2025/10/07
     */
    public Blockmen {
        rejectedBlocks = Set.copyOf(rejectedBlocks);
        acceptedBlocks = Set.copyOf(acceptedBlocks);
    }

    public static final Codec<Blockmen> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            // 使用 UUIDUtil.CODEC 处理 UUID
            UUIDUtil.CODEC.fieldOf("id").forGetter(Blockmen::id),
            // Set<String> 使用 Codec.STRING 列表并转换为 Set
            Codec.STRING.listOf().xmap(
                Set::copyOf,
                list -> list.stream().toList()
            ).fieldOf("rejectedBlocks").forGetter(Blockmen::rejectedBlocks),
            Codec.STRING.listOf().xmap(
                Set::copyOf,
                list -> list.stream().toList()
            ).fieldOf("acceptedBlocks").forGetter(Blockmen::acceptedBlocks)
        ).apply(instance, Blockmen::new)
    );

    public static final StreamCodec<ByteBuf, Blockmen> STREAM_CODEC = StreamCodec.composite(
        // 使用 UUIDUtil.STREAM_CODEC 处理 UUID
        UUIDUtil.STREAM_CODEC, Blockmen::id,
        // Set<String> 使用字符串列表编解码器并转换为 Set
        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())
            .map(Set::copyOf, set -> set.stream().toList()), Blockmen::rejectedBlocks,
        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())
            .map(Set::copyOf, set -> set.stream().toList()), Blockmen::acceptedBlocks,
        Blockmen::new
    );

    /**
     * 判断指定的方块键是否被接受。
     *
     * @param blockKey 要检查的方块键。
     * @return 如果指定的方块键在接受的集合中，则返回 true；否则返回 false。
     * @author sxt
     * @since 2025/10/07
     */
    public boolean isAccepted(String blockKey) {
        return this.acceptedBlocks.contains(blockKey);
    }

    /**
     * 判断指定的方块键是否被拒绝。
     *
     * @param blockKey 要检查的方块键。
     * @return 如果指定的方块键在拒绝的集合中，则返回 true；否则返回 false。
     * @author sxt
     * @since 2025/10/07
     */
    public boolean isRejected(String blockKey) {
        return this.rejectedBlocks.contains(blockKey);
    }

    /**
     * 返回一个新的 {@code Blockmen} 实例，并在原拒绝的方块集合基础上增加指定的方块键。
     *
     * @param blockKey 要添加到拒绝集合中的方块键。
     * @return 一个新的 {@code Blockmen} 实例，其中包含更新后的拒绝方块集合。
     * @author sxt
     * @since 2025/10/07
     */
    public Blockmen withRejectedBlock(String blockKey) {
        Set<String> newRejected = new HashSet<>(this.rejectedBlocks);
        newRejected.add(blockKey);
        return new Blockmen(this.id, newRejected, this.acceptedBlocks);
    }

    /**
     * 返回一个新的 {@code Blockmen} 实例，并在原接受的方块集合基础上增加指定的方块键。
     *
     * @param blockKey 要添加到接受集合中的方块键。
     * @return 一个新的 {@code Blockmen} 实例，其中包含更新后的接受方块集合。
     * @author sxt
     * @since 2025/10/07
     */
    public Blockmen withAcceptedBlock(String blockKey) {
        Set<String> newAccepted = new HashSet<>(this.acceptedBlocks);
        newAccepted.add(blockKey);
        return new Blockmen(this.id, this.rejectedBlocks, newAccepted);
    }

    /**
     * 创建一个新的 {@code Blockmen} 实例，其接受的和拒绝的集合均为空集合。
     *
     * @param id 唯一标识 {@code Blockmen} 的 UUID。
     * @return 一个新的 {@code Blockmen} 实例，其中包含指定的 id 和空的接受/拒绝集合。
     * @author sxt
     * @since 2025/10/07
     */
    public static Blockmen empty(UUID id) {
        return new Blockmen(id, Set.of(), Set.of());
    }
}
