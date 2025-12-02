package com.iicsadog.blocksblocks.core.job.empty;

import com.google.common.collect.ImmutableList;
import com.iicsadog.blocksblocks.api.job.Job;
import com.iicsadog.blocksblocks.core.entity.BlockmanEntity;
import net.minecraft.world.entity.schedule.Activity;

/**
 * 空的工作。
 *
 * @author sxtkl
 * @since 2025/12/2
 */
public class EmptyJob extends Job {

    /**
     * 空的工作。
     *
     * @author sxtkl
     * @since 2025/12/2
     */
    public EmptyJob() {
        super(ImmutableList.of(), ImmutableList.of());
    }

    @Override
    public void updateActivity(BlockmanEntity blockman) {
        blockman.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }
}
