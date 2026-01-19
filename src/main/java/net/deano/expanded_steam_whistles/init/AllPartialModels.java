package net.deano.expanded_steam_whistles.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;

public class AllPartialModels {
    public static final PartialModel


            EXPANDED_WHISTLE_MOUTH_HUGE = block("expanded_steam_whistle/expanded_steam_whistle_huge_mouth"),
            EXPANDED_WHISTLE_MOUTH_LARGE = block("expanded_steam_whistle/expanded_steam_whistle_large_mouth"),
            EXPANDED_WHISTLE_MOUTH_MEDIUM = block("expanded_steam_whistle/expanded_steam_whistle_medium_mouth"),
            EXPANDED_WHISTLE_MOUTH_SMALL = block("expanded_steam_whistle/expanded_steam_whistle_small_mouth"),
            EXPANDED_WHISTLE_MOUTH_TINY = block("expanded_steam_whistle/expanded_steam_whistle_tiny_mouth");


    private static PartialModel block(String path) {
        return PartialModel.of(ExpandedSteamWhistles.asResource("block/" + path));
    }

    public static void init() {
        // init static fields
    }

}
