package com.iicsadog.blockcolony.api.entity;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.core.entity.RisingItemEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
        .create(Registries.ENTITY_TYPE, BlockColony.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<RisingItemEntity>> RISING_ITEM_ENTITY =
        ENTITY_TYPES.register(
            "rising_item",
            () -> EntityType.Builder.<RisingItemEntity>of(RisingItemEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .eyeHeight(0.2125F)
                .clientTrackingRange(6)
                .updateInterval(20)
                .build("rising_item")
        );
}
