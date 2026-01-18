package net.deano.expanded_steam_whistles.datagen;

import net.deano.expanded_steam_whistles.datagen.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.client.model.generators.ModelFile;

public class BlockStateGen {

    public static class ExpandedWhistleGenerator extends SpecialBlockStateGen {

        @Override
        protected int getXRotation(BlockState state) {
            return 0;
        }

        @Override
        protected int getYRotation(BlockState state) {
            return horizontalAngle(state.getValue(ExpandedSteamWhistleBlock.FACING));
        }

        @Override
        public <T extends Block> ModelFile getModel(
                DataGenContext<Block, T> ctx,
                RegistrateBlockstateProvider prov,
                BlockState state
        ) {
            String size = state.getValue(ExpandedSteamWhistleBlock.SIZE).getSerializedName();
            String placement = state.getValue(ExpandedSteamWhistleBlock.WALL) ? "wall" : "floor";
            boolean powered = state.getValue(ExpandedSteamWhistleBlock.POWERED);

            ModelFile model = AssetLookup.partialStandardModel(ctx, prov, size, placement);
            if (!powered)
                return model;

            ResourceLocation parent = model.getLocation();
            return prov.models()
                    .withExistingParent(parent.getPath() + "_powered", parent)
                    .texture("0", "expanded_steam_whistles:block/copper_redstone_plate_powered");
        }
    }
    public static SpecialBlockStateGen whistleExtender() {
        return new SpecialBlockStateGen() {

            @Override
            protected int getXRotation(BlockState state) {
                return 0;
            }

            @Override
            protected int getYRotation(BlockState state) {
                return horizontalAngle(state.getValue(ExpandedSteamWhistleBlock.FACING));
            }

            @Override
            public <T extends Block> ModelFile getModel(
                    DataGenContext<Block, T> ctx,
                    RegistrateBlockstateProvider prov,
                    BlockState state
            ) {
                String size = state.getValue(ExpandedSteamWhistleBlock.SIZE).getSerializedName();
                return AssetLookup.partialStandardModel(ctx, prov, size);
            }
        };
    }

}
