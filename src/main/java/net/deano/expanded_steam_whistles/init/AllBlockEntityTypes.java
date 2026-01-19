package net.deano.expanded_steam_whistles.init;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleBlockEntity;
import net.deano.expanded_steam_whistles.whistle.ExpandedSteamWhistleRenderer;

public class AllBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = ExpandedSteamWhistles.registrate();


    public static final BlockEntityEntry<ExpandedSteamWhistleBlockEntity> EXPANDED_STEAM_WHISTLE = REGISTRATE
            .blockEntity("expanded_steam_whistle", ExpandedSteamWhistleBlockEntity::new)
            .validBlocks(AllBlocks.EXPANDED_STEAM_WHISTLE)
            .renderer(() -> ExpandedSteamWhistleRenderer::new)
            .register();

    public static void register() {
    }
}
