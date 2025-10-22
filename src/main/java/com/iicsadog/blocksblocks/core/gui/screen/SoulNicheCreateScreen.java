package com.iicsadog.blocksblocks.core.gui.screen;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.iicsadog.blocksblocks.api.network.ModChannels;
import com.iicsadog.blocksblocks.core.network.packet.ActivateSoulNichePacket;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

/**
 * 灵魂壁龛界面类，用于显示和创建殖民地。
 * 继承自BaseUIModelScreen，使用FlowLayout作为布局管理器。
 * 界面包含确认按钮、取消按钮和殖民地名称输入框。
 * 当用户点击确认按钮时，会将输入的殖民地名称发送到服务器，并关闭界面。
 * 当用户点击取消按钮时，直接关闭界面。
 *
 * @author sxtkl
 * @since 2025/10/15
 */
public class SoulNicheCreateScreen extends BaseUIModelScreen<FlowLayout> {

    private static final String CONFIRM_BUTTON = "confirm-button";
    private static final String CANCEL_BUTTON = "cancel-button";
    private static final String COLONY_NAME_INPUT = "colony-name-input";

    /**
     * 灵魂壁龛界面类的构造函数。
     * 初始化一个使用FlowLayout作为布局管理器的界面，
     * 并从资源位置加载界面布局文件。
     *
     * @author sxtkl
     * @since 2025/10/15
     */
    public SoulNicheCreateScreen() {
        super(
            FlowLayout.class,
            DataSource.asset(ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "soul_niche_create"))
        );
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(ButtonComponent.class, CANCEL_BUTTON)
            .onPress(button -> Minecraft.getInstance().setScreen(null));
        rootComponent.childById(ButtonComponent.class, CONFIRM_BUTTON)
            .onPress(button -> {
                    ModChannels.NET_CHANNEL.clientHandle().send(new ActivateSoulNichePacket(
                        rootComponent.childById(TextAreaComponent.class, COLONY_NAME_INPUT).getValue()
                    ));
                    Minecraft.getInstance().setScreen(null);
                }
            );
    }
}
