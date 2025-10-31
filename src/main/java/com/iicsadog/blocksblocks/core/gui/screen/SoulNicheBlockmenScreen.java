package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModRequests;
import com.iicsadog.blocksblocks.core.network.vo.BlockmenVO;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

/**
 * 灵魂壁龛方块人列表界面类，用于显示特定殖民地的所有方块人信息。
 * 继承自BaseUIModelScreen，使用FlowLayout作为布局管理器。
 * 该界面负责从服务器获取指定殖民地的方块人数据，并在界面上展示。
 *
 * @author sxtkl
 * @since 2025/10/26
 */
public class SoulNicheBlockmenScreen extends BaseUIModelScreen<FlowLayout> {

    private final List<BlockmenVO> vos = new ArrayList<>();
    private final UUID colonyId;

    /**
     * 灵魂壁龛方块人列表界面类的构造函数，用于创建并初始化一个显示特定殖民地方块人信息的界面。
     * 该界面使用FlowLayout作为布局管理器，并从指定资源位置加载界面布局。
     *
     * @param colonyId 殖民地的唯一标识符，用于获取该殖民地的方块人数据
     * @author sxtkl
     * @since 2025/10/26
     */
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
        ModRequests.getColonyBlockmen(colonyId)
            .success(res -> this.setVos(res.blockmen()))
            .send();
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

    /**
     * 设置方块人视图对象列表并更新界面显示。
     * 此方法会清空当前的方块人列表，添加新的方块人数据，
     * 然后重新构建界面以反映最新的方块人信息。
     *
     * @param vos 包含方块人视图对象的列表，用于更新界面显示
     * @author sxtkl
     * @since 2025/10/26
     */
    private void setVos(List<BlockmenVO> vos) {
        this.vos.clear();
        this.vos.addAll(vos);
        this.build(this.uiAdapter.rootComponent);
    }
}
