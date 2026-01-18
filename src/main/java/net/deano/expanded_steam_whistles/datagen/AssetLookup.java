package net.deano.expanded_steam_whistles.datagen;

import com.google.common.base.Function;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraftforge.client.model.generators.ModelFile;

public class AssetLookup {

    /**
     * Equivalent to Create's partialBaseModel
     * Example output:
     * block/steam_whistle/steam_whistle_small_wall
     */
    public static ModelFile partialStandardModel(
            DataGenContext<?, ?> ctx,
            RegistrateBlockstateProvider prov,
            String... suffix
    ) {
        String path = ctx.getName();

        for (String suf : suffix) {
            if (!suf.isEmpty())
                path += "_" + suf;
        }

        return prov.models()
                .getExistingFile(prov.modLoc("block/" + ctx.getName() + "/" + path));
    }

    //Item Models
    public static <T extends Item>
    NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> existingItemModel() {
        return (c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName()));
    }

    //State Helpers
    public static Function<BlockState, ModelFile> forPowered(
            DataGenContext<?, ?> ctx,
            RegistrateBlockstateProvider prov
    ) {
        return state -> state.getValue(BlockStateProperties.POWERED)
                ? partialStandardModel(ctx, prov, "powered")
                : partialStandardModel(ctx, prov);
    }

    public static Function<BlockState, ModelFile> forBooleanProperty(
            BooleanProperty property,
            String suffix,
            DataGenContext<?, ?> ctx,
            RegistrateBlockstateProvider prov
    ) {
        return state -> state.getValue(property)
                ? partialStandardModel(ctx, prov, suffix)
                : partialStandardModel(ctx, prov);
    }
}
