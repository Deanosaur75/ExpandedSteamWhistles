package net.deano.expanded_steam_whistles.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;

public class AllPartialModels {
    public static final PartialModel


            EXPANDED_WHISTLE_MOUTH_HUGE = block("steam_whistle/huge_mouth"),
            EXPANDED_WHISTLE_MOUTH_LARGE = block("steam_whistle/large_mouth"),
            EXPANDED_WHISTLE_MOUTH_MEDIUM = block("steam_whistle/medium_mouth"),
            EXPANDED_WHISTLE_MOUTH_SMALL = block("steam_whistle/small_mouth"),
            EXPANDED_WHISTLE_MOUTH_TINY = block("steam_whistle/tiny_mouth");


    private static PartialModel block(String path) {
        return PartialModel.of(ExpandedSteamWhistles.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return PartialModel.of(ExpandedSteamWhistles.asResource("entity/" + path));
    }

    public static void init() {
        // init static fields
    }

}
