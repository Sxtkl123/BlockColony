package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.iicsadog.blocksblocks.core.network.packet.request.client.GetColonyBlockmenC2S;
import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public class SoulNicheBlockmenScreen extends BaseUIModelScreen<FlowLayout> {

    private final List<BlockmenVO> vos = new ArrayList<>();
    private final UUID colonyId;

    public SoulNicheBlockmenScreen(UUID colonyId) {
        super(
            FlowLayout.class,
            DataSource.asset(ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "soul_niche_blockmen"))
        );
        this.colonyId = colonyId;
    }

    @Override
    protected void init() {
        super.init();
        ModChannels.NET_CHANNEL.clientHandle().send(new GetColonyBlockmenC2S(this.colonyId));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        List<FlowLayout> info = this.vos.stream()
            .map(vo -> this.model.expandTemplate(FlowLayout.class, "info", Map.of("name", vo.name())))
            .toList();
        rootComponent.childById(FlowLayout.class, "info-container").children(
            info
        );
    }

    public void setVos(List<BlockmenVO> vos) {
        this.vos.clear();
        this.vos.addAll(vos);
        this.build(this.uiAdapter.rootComponent);
    }
}
