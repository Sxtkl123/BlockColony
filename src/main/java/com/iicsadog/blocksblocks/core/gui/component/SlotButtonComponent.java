package com.iicsadog.blocksblocks.core.gui.component;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.wispforest.owo.ui.base.BaseComponent;
import io.wispforest.owo.ui.core.CursorStyle;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.parsing.UIModel;
import io.wispforest.owo.ui.parsing.UIModelParsingException;
import io.wispforest.owo.ui.parsing.UIParsing;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.w3c.dom.Element;

/**
 * 一个用于显示物品的按钮组件，继承自BaseComponent。
 * 该组件可以显示一个物品堆栈，并在鼠标悬停时显示高亮效果。
 * 点击按钮时会触发预设的回调函数。
 *
 * @author sxtkl
 * @since 2025/10/24
 */
@SuppressWarnings("UnusedReturnValue")
public class SlotButtonComponent extends BaseComponent {

    private final TextureAtlasSprite frame;
    private final TextureAtlasSprite hoveredFrame;

    private final MultiBufferSource.BufferSource entityBuffers;
    private final ItemRenderer itemRenderer;
    private ItemStack stack;
    private Consumer<SlotButtonComponent> onPress;

    /**
     * 创建一个带有物品的按钮组件。
     *
     * @param stack 要显示在按钮中的物品堆栈
     * @author sxtkl
     * @since 2025/10/24
     */
    protected SlotButtonComponent(ItemStack stack) {
        this.cursorStyle(CursorStyle.HAND);

        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        this.entityBuffers = Minecraft.getInstance().renderBuffers().bufferSource();
        this.frame = Minecraft.getInstance().getGuiSprites().getSprite(ResourceLocation.withDefaultNamespace("gamemode_switcher/slot"));
        this.hoveredFrame = Minecraft.getInstance().getGuiSprites().getSprite(ResourceLocation.withDefaultNamespace("gamemode_switcher/selection"));
        this.stack = stack;
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
        Lighting.setupForFlatItems();

        context.blit(this.x, this.y, 0, this.width, this.height, this.frame);
        if (hovered) {
            context.blit(this.x, this.y, 0, this.width, this.height, this.hoveredFrame);
        }

        PoseStack matrices = context.pose();
        matrices.pushPose();

        // Translate to the root of the component
        matrices.translate(this.x + this.width * 3 / 14f, this.y + this.height * 3 / 14f, 100);

        // Scale according to component size and translate to the center
        matrices.scale(this.width / 28f, this.height / 28f, 1);
        matrices.translate(8.0, 8.0, 0.0);

        // Vanilla scaling and y inversion
        matrices.scale(16, -16, 16);

        Minecraft client = Minecraft.getInstance();

        this.itemRenderer.renderStatic(this.stack, ItemDisplayContext.GUI, LightTexture.FULL_BRIGHT,
            OverlayTexture.NO_OVERLAY, matrices, entityBuffers, client.level, 0);
        this.entityBuffers.endBatch();

        // Clean up
        matrices.popPose();
    }

    @Override
    protected int determineHorizontalContentSize(Sizing sizing) {
        return 28;
    }

    @Override
    protected int determineVerticalContentSize(Sizing sizing) {
        return 28;
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        boolean result = super.onMouseDown(mouseX, mouseY, button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            // TODO)) 需要添加一个播放按钮被点击音效的功能
            if (this.onPress != null) {
                this.onPress.accept(this);
                return true;
            }
        }

        return result;
    }

    /**
     * 设置要显示在按钮中的物品堆栈。
     *
     * @param stack 要显示的物品堆栈
     * @return 当前组件实例，用于链式调用
     * @author sxtkl
     * @since 2025/10/24
     */
    public SlotButtonComponent stack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    /**
     * 设置按钮被按下时的回调函数。
     *
     * @param onPress 按钮被按下时执行的消费者函数，接收当前按钮组件作为参数
     * @return 当前组件实例，用于链式调用
     * @author sxtkl
     * @since 2025/10/24
     */
    public SlotButtonComponent onPress(Consumer<SlotButtonComponent> onPress) {
        this.onPress = onPress;
        return this;
    }

    @Override
    public void parseProperties(UIModel model, Element element, Map<String, Element> children) {
        super.parseProperties(model, element, children);

        UIParsing.apply(children, "stack", ele -> ele.getTextContent().strip(), stackString -> {
            try {
                ItemParser.ItemResult result = new ItemParser(HolderLookup.Provider.create(Stream.of(BuiltInRegistries.ITEM.asLookup())))
                    .parse(new StringReader(stackString));

                ItemStack stack = new ItemStack(result.item());
                stack.applyComponentsAndValidate(result.components());

                this.stack(stack);
            } catch (CommandSyntaxException ex) {
                throw new UIModelParsingException("Invalid item stack", ex);
            }
        });
    }
}
