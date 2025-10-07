package com.iicsadog.blocksblocks.core.manager.client;

import com.iicsadog.blocksblocks.BlocksBlocks;
import com.mojang.blaze3d.platform.NativeImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 方块酱材质管理器，用于管理动态生成的方块酱材质。该类只需要在客户端存在。
 *
 * @author sxtkl
 * @since 2025/10/1
 */
@OnlyIn(Dist.CLIENT)
public class BlockmanTextureManager {

    private static final Map<String, ResourceLocation> CACHE = new HashMap<>();
    private static int textureCount = 0;

    /**
     * 根据方块状态获得对应的方块酱材质。
     *
     * @param blockState 方块状态。
     * @return 材质位置。
     * @author sxtkl
     * @since 2025/10/1
     */
    public static ResourceLocation getTexture(BlockState blockState) {
        String key = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString().replace(":", "_");
        if  (!CACHE.containsKey(key)) {
            CACHE.put(key, generateBlockStateTexture(blockState));
        }
        return CACHE.get(key);
    }

    private static ResourceLocation generateBlockStateTexture(BlockState blockstate) {
        try {
            NativeImage image = generateTextureContent(blockstate);
            Minecraft mc =  Minecraft.getInstance();
            DynamicTexture texture = new DynamicTexture(image);
            ResourceLocation location =
                ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "blockman_" + textureCount);
            mc.getTextureManager().register(location, texture);
            textureCount++;
            return location;
        } catch (Exception e) {
            return ResourceLocation.fromNamespaceAndPath(BlocksBlocks.MODID, "textures/entity/blockman.png");
        }
    }

    private static NativeImage generateTextureContent(BlockState blockState) {
        NativeImage image = new NativeImage(64, 64, false);
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                image.setPixelRGBA(i, j, 0x00000000);
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                image.setPixelRGBA(i, j, 0xff000000);
            }
        }

        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState);
        for (Direction direction : Direction.values()) {
            TextureAtlasSprite sprite = getTextureForDirection(model, blockState, direction);
            if (sprite != null) {
                copyPixel(image, sprite, direction);
            }
        }

        return image;
    }

    private static void copyPixel(NativeImage dest, TextureAtlasSprite src, Direction direction) {
        int offsetX = 0;
        int offsetY = 0;
        switch (direction) {
            case UP:
                offsetX = 16;
                break;
            case DOWN:
                offsetX = 32;
                break;
            case NORTH:
                offsetX = 16;
                offsetY = 16;
                break;
            case SOUTH:
                offsetX = 48;
                offsetY = 16;
                break;
            case WEST:
                offsetX = 32;
                offsetY = 16;
                break;
            case EAST:
                offsetY = 16;
                break;
            default:
                break;
        }
        for  (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                dest.setPixelRGBA(x + offsetX, y + offsetY, src.getPixelRGBA(0, x, y));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static TextureAtlasSprite getTextureForDirection(BakedModel model, BlockState state, Direction direction) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        // 获取该方向的所有四边形
        List<BakedQuad> quads = model.getQuads(state, direction, level.random);

        if (!quads.isEmpty()) {
            BakedQuad quad = quads.getFirst();
            return quad.getSprite();
        }

        quads = model.getQuads(state, null, Minecraft.getInstance().level.random);
        if (!quads.isEmpty()) {
            return quads.getFirst().getSprite();
        }

        return null;
    }

}
