package net.createcobblestone.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CobblestoneGeneratorRenderer extends KineticBlockEntityRenderer<CobblestoneGeneratorBlockEntity> {
    public CobblestoneGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CobblestoneGeneratorBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Block renderedBlock = be.type.getBlock();

        if (renderedBlock == Blocks.AIR || renderedBlock == null) {
            renderedBlock = Blocks.BARRIER;
        }

        ItemStack renderedItem = new ItemStack(renderedBlock);

        ms.pushPose();
        ms.translate(0.5, 1, 0.5); // Adjust position as needed
        Minecraft.getInstance().getItemRenderer().renderStatic(renderedItem, ItemDisplayContext.GROUND, 255 - light, overlay, ms, buffer, be.getLevel(), 0);
        ms.popPose();

        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
    }
}
