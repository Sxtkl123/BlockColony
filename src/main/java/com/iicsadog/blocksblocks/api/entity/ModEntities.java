package com.iicsadog.blocksblocks.api.entity;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import com.iicsadog.blocksblocks.core.entity.RisingItemEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 存储所有实体的类。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
        .create(Registries.ENTITY_TYPE, BlocksBlocks.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<RisingItemEntity>> RISING_ITEM =
        ENTITY_TYPES.register(
            "rising_item",
            () -> EntityType.Builder.<RisingItemEntity>of(RisingItemEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .eyeHeight(0.2125F)
                .clientTrackingRange(6)
                .updateInterval(20)
                .build("rising_item")
        );

    public static final DeferredHolder<EntityType<?>, EntityType<BlockmanEntity>> BLOCKMAN =
        ENTITY_TYPES.register(
            "blockman",
            () -> EntityType.Builder.<BlockmanEntity>of(BlockmanEntity::new, MobCategory.MISC)
                .sized(0.6F, 1.375F)
                .build("blockman")
        );
}
