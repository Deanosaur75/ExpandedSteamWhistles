package net.deano.expandedsteamwhistles.event;

import net.deano.expandedsteamwhistles.ExpandedSteamWhistles;
import net.deano.expandedsteamwhistles.init.AllBlockEntities;
import net.deano.expandedsteamwhistles.whistle.ExpandedSteamWhistleRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExpandedSteamWhistles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AllBlockEntities.EXPANDED_STEAM_WHISTLE_BLOCK_ENTITY.get(), ExpandedSteamWhistleRenderer::new);
    }
}
