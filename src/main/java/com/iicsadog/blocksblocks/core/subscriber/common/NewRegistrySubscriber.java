package com.iicsadog.blocksblocks.core.subscriber.common;

import com.iicsadog.blocksblocks.api.ModRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber
public class NewRegistrySubscriber {

    @SubscribeEvent
    public static void newRegistry(NewRegistryEvent evt) {
        evt.register(ModRegistries.JOB);
    }

}
