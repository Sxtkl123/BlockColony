package com.iicsadog.blocksblocks.api;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.job.Job;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * 模组中所有新的可注册类。
 *
 * @author sxtkl
 * @since 2025/12/2
 */
public class ModRegistries {

    public static final ResourceKey<Registry<Job>> JOB_KEY = ResourceKey.createRegistryKey(
        BlocksBlocks.namespace("job"));

    public static final Registry<Job> JOB = new RegistryBuilder<>(JOB_KEY).sync(true).create();

}
