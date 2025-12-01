package com.iicsadog.blocksblocks.api.job;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.ModRegistries;
import com.iicsadog.blocksblocks.core.job.empty.EmptyJob;
import com.iicsadog.blocksblocks.core.job.lumberjack.LumberjackJob;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModJobs {

    public static final DeferredRegister<Job> JOBS =
        DeferredRegister.create(ModRegistries.JOB, BlocksBlocks.MODID);

    public static final DeferredHolder<Job, LumberjackJob> LUMBERJACK =
        JOBS.register("lumberjack", LumberjackJob::new);

    public static final DeferredHolder<Job, EmptyJob> EMPTY =
        JOBS.register("empty", EmptyJob::new);

}
