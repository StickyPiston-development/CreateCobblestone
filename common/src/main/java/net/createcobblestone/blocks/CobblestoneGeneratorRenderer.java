package net.createcobblestone.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CobblestoneGeneratorRenderer extends KineticBlockEntityRenderer<CobblestoneGeneratorBlockEntity> {
    public CobblestoneGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

//    @Override
//    public void renderSafe(CobblestoneGeneratorBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
//        // Determine the item to render based on the block entity's enum data
//
//        if (blockEntity.type.getBlock() != null) {
//            matrixStack.pushPose();
//            matrixStack.translate(0.5, 1.0, 0.5); // Adjust position as needed
//            Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(blockEntity.type.getBlock()), ItemDisplayContext.GROUND, combinedLight, combinedOverlay, matrixStack, buffer, blockEntity.getLevel(), 0);
//            matrixStack.popPose();
//        }
//    }
}
