package net.deano.expanded_steam_whistles.datagen;

import net.deano.expanded_steam_whistles.datagen.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleExtensionBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.client.model.generators.ModelFile;

import static net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock.SIZE;
import static net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleExtensionBlock.SHAPE;

public class BlockStateGen {

    public static class ExpandedWhistleGenerator extends SpecialBlockStateGen {

        @Override
        protected int getXRotation(BlockState state) { return 0; }

        @Override
        protected int getYRotation(BlockState state) {
            return horizontalAngle(state.getValue(ExpandedSteamWhistleBlock.FACING));
        }

        @Override
        public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx,
                                                    RegistrateBlockstateProvider prov,
                                                    BlockState state) {
            String size = state.getValue(SIZE).getSerializedName();
            String placement = state.getValue(ExpandedSteamWhistleBlock.WALL) ? "wall" : "floor";

            ModelFile model = AssetLookup.partialBaseModel(ctx, prov, size, placement);

            if (state.getValue(ExpandedSteamWhistleBlock.POWERED)) {
                return prov.models()
                        .withExistingParent(size + "_" + placement + "_powered", model.getLocation())
                        .texture("0", "expanded_steam_whistles:block/copper_redstone_plate_powered");
            }

            return model;
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
                // Extension inherits FACING from the base block
                return state.hasProperty(ExpandedSteamWhistleBlock.FACING)
                        ? horizontalAngle(state.getValue(ExpandedSteamWhistleBlock.FACING))
                        : 0;
            }

            @Override
            public <T extends Block> ModelFile getModel(
                    DataGenContext<Block, T> ctx,
                    RegistrateBlockstateProvider prov,
                    BlockState state
            ) {
                String size = state.getValue(SIZE).getSerializedName();
                String shape = state.getValue(SHAPE).getSerializedName().toLowerCase(); // "single", "double", "double_connected"
                return AssetLookup.partialExtensionModel(ctx, prov, size, shape);
            }
        };
    }
}