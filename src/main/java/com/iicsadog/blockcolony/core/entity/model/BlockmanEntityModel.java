package com.iicsadog.blockcolony.core.entity.model;

import com.iicsadog.blockcolony.BlockColony;
import com.iicsadog.blockcolony.core.entity.BlockmanEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 方块酱模型。
 *
 * @author sxtkl
 * @since 2025/9/29
 */
public class BlockmanEntityModel extends EntityModel<BlockmanEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath(BlockColony.MODID, "blockman"),
        "main"
    );
    private final ModelPart whole;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    /**
     * 方块酱模型的构造方法，用来在渲染器中指定模型。
     *
     * @param root 渲染根
     * @author sxtkl
     * @since 2025/9/29
     */
    public BlockmanEntityModel(ModelPart root) {
        this.whole = root.getChild("whole");
        this.leftLeg = this.whole.getChild("left_leg");
        this.rightLeg = this.whole.getChild("right_leg");
    }

    /**
     * 用于创建并定义方块人模型的结构和外观。
     *
     * @return 模型结构与外观的定义
     * @author sxtkl
     * @since 2025/9/29
     */
    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition whole = partdefinition.addOrReplaceChild("whole",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, -22.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(12, 12)
                .addBox(9.0F, -14.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 12)
                .addBox(-13.0F, -14.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(2, 2)
                .addBox(-5.0F, -19.0F, -8.25F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 2)
                .addBox(3.0F, -19.0F, -8.25F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftLeg = whole.addOrReplaceChild("left_leg",
            CubeListBuilder.create().texOffs(36, 12)
                .addBox(-2.0F, 4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(4.0F, -8.0F, 0.0F));

        PartDefinition rightLeg = whole.addOrReplaceChild("right_leg",
            CubeListBuilder.create().texOffs(36, 12)
                .addBox(-2.0F, 4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-4.0F, -8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull BlockmanEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int color) {
        whole.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}