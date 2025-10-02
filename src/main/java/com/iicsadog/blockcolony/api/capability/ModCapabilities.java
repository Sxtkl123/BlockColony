package com.iicsadog.blockcolony.api.capability;

import com.iicsadog.blockcolony.BlockColony;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.ItemCapability;

/**
 * 模组的能力。
 *
 * @author sxtkl
 * @since 2025/10/3
 */
public class ModCapabilities {

    public static final ItemCapability<IBlockmanDataStorage, Void> BLOCKMAN_DATA_STORAGE_CAPABILITY =
        ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(BlockColony.MODID, "blockman_data_storage_capability"),
            IBlockmanDataStorage.class
        );

}
