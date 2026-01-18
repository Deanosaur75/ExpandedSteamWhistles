package net.deano.expanded_steam_whistles.whistle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;

import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock.ExpandedWhistleSize;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.deano.expanded_steam_whistles.init.AllPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class ExpandedSteamWhistleRenderer extends SafeBlockEntityRenderer<ExpandedSteamWhistleBlockEntity> {

    public ExpandedSteamWhistleRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(ExpandedSteamWhistleBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockState blockState = be.getBlockState();
        if (!(blockState.getBlock() instanceof ExpandedSteamWhistleBlock))
            return;

        Direction direction = blockState.getValue(ExpandedSteamWhistleBlock.FACING);
        ExpandedSteamWhistleBlock.ExpandedWhistleSize size = blockState.getValue(ExpandedSteamWhistleBlock.SIZE);

        PartialModel mouth = size == ExpandedWhistleSize.HUGE ? AllPartialModels.EXPANDED_WHISTLE_MOUTH_HUGE
                : size == ExpandedWhistleSize.LARGE ? AllPartialModels.EXPANDED_WHISTLE_MOUTH_LARGE
                : size == ExpandedWhistleSize.MEDIUM ? AllPartialModels.EXPANDED_WHISTLE_MOUTH_MEDIUM
                : size == ExpandedWhistleSize.SMALL ? AllPartialModels.EXPANDED_WHISTLE_MOUTH_SMALL
                : AllPartialModels.EXPANDED_WHISTLE_MOUTH_TINY;

        float offset = be.animation.getValue(partialTicks);
        if (be.animation.getChaseTarget() > 0 && be.animation.getValue() > 0.5f) {
            float wiggleProgress = (AnimationTickHolder.getTicks(be.getLevel()) + partialTicks) / 8f;
            offset -= Math.sin(wiggleProgress * (2 * Mth.PI) * (4 - size.ordinal())) / 16f;
        }

        CachedBuffers.partial(mouth, blockState)
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(direction))
                .uncenter()
                .translate(0, offset * 4 / 16f, 0)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

}