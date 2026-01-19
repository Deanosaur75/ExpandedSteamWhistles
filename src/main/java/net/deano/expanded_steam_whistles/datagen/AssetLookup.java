package net.deano.expanded_steam_whistles.datagen;

import com.google.common.base.Function;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleExtensionBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ModelFile;

/**
 * AssetLookup for expanded_steam_whistles
 * Handles:
 * - Base whistle models
 * - Extension models (single, double, double_connected)
 * - Item models
 * - Powered / boolean variants
 */
public class AssetLookup {

    /**
     * Base whistle model
     * Example: block/expanded_steam_whistle/expanded_steam_whistle_small_wall
     */
    public static ModelFile whistleBaseModel(
            RegistrateBlockstateProvider prov,
            String size,
            String placement
    ) {
        return prov.models().getExistingFile(
                prov.modLoc("block/expanded_steam_whistle/expanded_steam_whistle_" + size + "_" + placement)
        );
    }

    /**
     * Extension model
     * Example: block/expanded_steam_whistle/extension/expanded_steam_whistle_tiny_single
     */
    public static ModelFile whistleExtensionModel(
            RegistrateBlockstateProvider prov,
            String size,
            String shape
    ) {
        return prov.models().getExistingFile(
                prov.modLoc("block/expanded_steam_whistle/extension/expanded_steam_whistle_" + size + "_" + shape)
        );
    }

    /**
     * Standard item models
     */
    public static <T extends Item>
    NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> existingItemModel() {
        return (c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName()));
    }

    /**
     * Model for powered blocks (like whistles that can be "powered")
     */
    public static Function<BlockState, ModelFile> forPowered(
            RegistrateBlockstateProvider prov,
            DataGenContext<?, ?> ctx
    ) {
        return state -> state.getValue(BlockStateProperties.POWERED)
                ? whistleBaseModel(prov, "default", "powered")
                : whistleBaseModel(prov, "default", "normal");
    }

    /**
     * Helper for any boolean property (like wall/floor)
     */
    public static Function<BlockState, ModelFile> forBooleanProperty(
            BooleanProperty property,
            String suffix,
            RegistrateBlockstateProvider prov,
            DataGenContext<?, ?> ctx
    ) {
        return state -> state.getValue(property)
                ? whistleBaseModel(prov, "default", suffix)
                : whistleBaseModel(prov, "default", "normal");
    }
}
