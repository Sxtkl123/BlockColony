package com.iicsadog.blocksblocks.core.subscriber.common;

import com.iicsadog.blocksblocks.api.block.BaseHutEntityBlock;
import com.iicsadog.blocksblocks.api.block.entity.BaseHutBlockEntity;
import com.iicsadog.blocksblocks.api.manager.DataManagers;
import com.iicsadog.blocksblocks.core.data.BuildingData;
import com.iicsadog.blocksblocks.core.data.ColonyData;
import com.iicsadog.blocksblocks.core.manager.data.BuildingDataManager;
import com.iicsadog.blocksblocks.core.manager.data.ColonyDataManager;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * 实体放置方块订阅器。
 *
 * @author sxtkl
 * @since 2025/11/7
 */
@EventBusSubscriber
public class EntityPlaceSubscriber {

    /**
     * 放置小屋方块事件，放置后将小屋注册到表中。
     *
     * @param event 事件
     * @author sxtkl
     * @since 2025/11/7
     */
    @SubscribeEvent
    public static void onPlaceHutBlock(BlockEvent.EntityPlaceEvent event) {
        // 如果放置的方块不是Hut方块，则不处理
        if (!(event.getPlacedBlock().getBlock() instanceof BaseHutEntityBlock)) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getLevel().isClientSide()) {
            return;
        }

        if (!(event.getLevel().getBlockEntity(event.getPos()) instanceof BaseHutBlockEntity hutEntity)) {
            return;
        }

        ColonyData colony = DataManagers.getInstance(ColonyDataManager::new).getPlayerColony(player.getUUID());
        if (colony == null) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.translatable("message.blocks_blocks.no_colony"));
            return;
        }
        UUID id = UUID.randomUUID();
        hutEntity.setBuildingId(id);
        BuildingData data = new BuildingData(
            id, colony.getId(), hutEntity.hutType().toString(),
            0, null, event.getPos()
        );
        data.setDimension((ServerLevel) event.getLevel());
        DataManagers.getInstance(BuildingDataManager::new).save(data);
    }
}
