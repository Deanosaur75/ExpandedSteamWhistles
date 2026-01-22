package net.deano.expanded_steam_whistles.init;

import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExpandedSteamWhistles.MOD_ID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WHISTLE_TAB = CREATIVE_MODE_TABS.register("whistle_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllBlocks.EXPANDED_STEAM_WHISTLE.get()))
                    .title(Component.translatable("creativetab.whistle_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(AllBlocks.EXPANDED_STEAM_WHISTLE.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
