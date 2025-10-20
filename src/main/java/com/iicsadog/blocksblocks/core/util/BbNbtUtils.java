package com.iicsadog.blocksblocks.core.util;

import com.iicsadog.blocksblocks.api.data.IData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;


/**
 * BbNbtUtils 提供了用于在 CompoundTag 中保存和加载 UUID 映射关系的工具方法。
 * 该类主要用于处理游戏中各种 UUID 映射数据的序列化和反序列化操作。
 *
 * @author sxtkl
 * @since 2025/10/20
 */
public class BbNbtUtils {

    /**
     * 从 CompoundTag 中加载 UUID 到 UUID 的映射关系。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 包含映射数据的 CompoundTag 对象
     * @return 包含 UUID 映射关系的 Map，如果指定的名称不存在则返回空集合
     * @author sxtkl
     * @since 2025/10/20
     */
    public static Map<UUID, UUID> loadMap(String name, CompoundTag tag) {
        Map<UUID, UUID> res = new HashMap<>();
        if (!tag.contains(name)) {
            return res;
        }
        CompoundTag target = tag.getCompound(name);
        for (String key : target.getAllKeys()) {
            res.put(UUID.fromString(key), target.getUUID(key));
        }
        return res;
    }

    /**
     * 从 CompoundTag 中加载 UUID 到 UUID 列表的映射关系。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 包含映射数据的 CompoundTag 对象
     * @return 包含 UUID 映射关系的 Map，如果指定的名称不存在则返回空集合
     * @author sxtkl
     * @since 2025/10/20
     */
    public static Map<UUID, List<UUID>> loadMapList(String name, CompoundTag tag) {
        Map<UUID, List<UUID>> res = new HashMap<>();
        if (!tag.contains(name)) {
            return res;
        }
        CompoundTag target = tag.getCompound(name);
        for (String key : target.getAllKeys()) {
            List<UUID> ids = new ArrayList<>();
            ListTag idTag = target.getList(key, Tag.TAG_STRING);
            idTag.forEach(ele -> {
                if (ele instanceof StringTag) {
                    ids.add(UUID.fromString(ele.getAsString()));
                }
            });
            res.put(UUID.fromString(key), ids);
        }
        return res;
    }

    /**
     * 从 CompoundTag 中加载 UUID 到 IData 对象的映射关系。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 包含映射数据的 CompoundTag 对象
     * @param init 用于创建新数据对象的 Supplier
     * @return 包含 UUID 到 IData 对象映射关系的 Map，如果指定的名称不存在则返回空集合
     * @author sxtkl
     * @since 2025/10/20
     */
    public static <T extends IData<T>> Map<UUID, T> loadMapData(String name, CompoundTag tag, Supplier<T> init) {
        Map<UUID, T> res = new HashMap<>();
        if (!tag.contains(name)) {
            return res;
        }
        CompoundTag target = tag.getCompound(name);
        for (String key : target.getAllKeys()) {
            T data = init.get().load(target.getCompound(key));
            res.put(UUID.fromString(key), data);
        }
        return res;
    }

    /**
     * 将 UUID 到 UUID 的映射关系保存到指定的 CompoundTag 中。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 用于保存映射数据的 CompoundTag 对象
     * @param map 包含 UUID 映射关系的 Map，将被保存到 CompoundTag 中
     * @author sxtkl
     * @since 2025/10/20
     */
    public static void saveMap(String name, CompoundTag tag, Map<UUID, UUID> map) {
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<UUID, UUID> entry : map.entrySet()) {
            mapTag.putUUID(entry.getKey().toString(), entry.getValue());
        }
        tag.put(name, mapTag);
    }

    /**
     * 将 UUID 到 UUID 列表的映射关系保存到指定的 CompoundTag 中。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 用于保存映射数据的 CompoundTag 对象
     * @param map 包含 UUID 到 UUID 列表映射关系的 Map，将被保存到 CompoundTag 中
     * @author sxtkl
     * @since 2025/10/20
     */
    public static void saveMapList(String name, CompoundTag tag, Map<UUID, List<UUID>> map) {
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<UUID, List<UUID>> entry : map.entrySet()) {
            UUID key = entry.getKey();
            ListTag tags = new ListTag();
            entry.getValue().forEach(id -> tags.add(StringTag.valueOf(id.toString())));
            mapTag.put(key.toString(), tags);
        }
        tag.put(name, mapTag);
    }

    /**
     * 将 UUID 到 IData 对象的映射关系保存到指定的 CompoundTag 中。
     *
     * @param name 在 CompoundTag 中用于标识映射的名称
     * @param tag 用于保存映射数据的 CompoundTag 对象
     * @param map 包含 UUID 到 IData 对象映射关系的 Map，将被保存到 CompoundTag 中
     * @author sxtkl
     * @since 2025/10/20
     */
    public static <T extends IData<T>> void saveMapData(String name, CompoundTag tag, Map<UUID, T> map) {
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<UUID, T> entry : map.entrySet()) {
            UUID key = entry.getKey();
            T data = entry.getValue();
            mapTag.put(key.toString(), data.save(new CompoundTag()));
        }
        tag.put(name, mapTag);
    }

}
