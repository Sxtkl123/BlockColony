package com.iicsadog.blockcolony.core.entity.renderer;

import com.iicsadog.blockcolony.core.entity.BlockmanEntity;
import com.iicsadog.blockcolony.core.entity.model.BlockmanEntityModel;
import com.iicsadog.blockcolony.core.manager.client.BlockmanTextureManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 方块人渲染器，暂时直接继承自生物渲染器，后面需要对其实现自定义材质功能。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
public class BlockmanEntityRenderer extends MobRenderer<BlockmanEntity, BlockmanEntityModel> {
    /**
     * 方块酱渲染器构造方法。
     *
     * @param context 构造环境（我总是觉得Context应该翻译为环境）
     * @author sxtkl
     * @since 2025/9/29
     */
    public BlockmanEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BlockmanEntityModel(context.bakeLayer(BlockmanEntityModel.LAYER_LOCATION)), 0.65f);
    }

    @Override
    public void render(
        @NotNull BlockmanEntity entity,
        float entityYaw,
        float partialTicks,
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource buffer,
        int packedLight
    ) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlockmanEntity entity) {
        return BlockmanTextureManager.getTexture(entity.getBlockState());
    }
}
