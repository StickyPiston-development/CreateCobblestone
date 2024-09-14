package net.createcobblestone.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class CobblestoneGeneratorRenderer extends KineticBlockEntityRenderer<CobblestoneGeneratorBlockEntity> {
    public CobblestoneGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CobblestoneGeneratorBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Block renderedBlock = be.type.getBlock();

        if (renderedBlock == null) {
            return;
        }

        ItemStack renderedItem = new ItemStack(renderedBlock);

        Direction shaftDirection = be.getBlockState().getValue(CobblestoneGeneratorBlock.HORIZONTAL_FACING);

        // Render on non-shaft sides based on the shaft's orientation
        if (shaftDirection == Direction.EAST || shaftDirection == Direction.WEST) {
            // Shaft is east-west, render on top, bottom, north, and south
            renderOnSides(ms, buffer, be, renderedItem, light, overlay, new Vec3[]{
                    new Vec3(0.5, 1, 0.5),  // Top
                    new Vec3(0.5, 0, 0.5),  // Bottom
                    new Vec3(0.5, 0.5, 1),  // North
                    new Vec3(0.5, 0.5, 0)   // South
            });
        } else if (shaftDirection == Direction.NORTH || shaftDirection == Direction.SOUTH) {
            // Shaft is north-south, render on top, bottom, east, and west
            renderOnSides(ms, buffer, be, renderedItem, light, overlay, new Vec3[]{
                    new Vec3(0.5, 1, 0.5),  // Top
                    new Vec3(0.5, 0, 0.5),  // Bottom
                    new Vec3(0, 0.5, 0.5),  // West
                    new Vec3(1, 0.5, 0.5)   // East
            });
        }

        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
    }

    private void renderOnSides(PoseStack ms, MultiBufferSource buffer, CobblestoneGeneratorBlockEntity be, ItemStack renderedItem, int light, int overlay, Vec3[] translations) {
        for (int i = 0; i < translations.length; i++) {
            ms.pushPose();
            ms.translate(translations[i].x, translations[i].y, translations[i].z); // Adjust position
            ms.scale(0.5f, 0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderStatic(renderedItem, ItemDisplayContext.FIXED, 255 - light, overlay, ms, buffer, be.getLevel(), 0);
            ms.popPose();
        }
    }
}
