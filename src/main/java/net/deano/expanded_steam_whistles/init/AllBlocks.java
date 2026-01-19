package net.deano.expanded_steam_whistles.init;

import net.deano.expanded_steam_whistles.datagen.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlock;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleExtensionBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

@SuppressWarnings("SameParameterValue")
public class AllBlocks {

    private static final CreateRegistrate REGISTRATE = ExpandedSteamWhistles.registrate();

    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.WHISTLE_TAB);
    }

    // Main whistle
    public static final BlockEntry<ExpandedSteamWhistleBlock> EXPANDED_STEAM_WHISTLE =
            REGISTRATE.block("expanded_steam_whistle", ExpandedSteamWhistleBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD))
                    .transform(pickaxeOnly())
                    .blockstate(new BlockStateGen.ExpandedWhistleGenerator()::generate)
                    .item()
                    .transform(customItemModel())
                    .register();

    // Extension block
    public static final BlockEntry<ExpandedSteamWhistleExtensionBlock> EXPANDED_STEAM_WHISTLE_EXTENSION =
            REGISTRATE.block("expanded_steam_whistle_extension", ExpandedSteamWhistleExtensionBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD)
                            .forceSolidOn())
                    .transform(pickaxeOnly())
                    .blockstate(BlockStateGen.whistleExtender()::generate) // << important fix
                    .register();

    public static void register() {
        // Ensures this class is loaded
    }
}
