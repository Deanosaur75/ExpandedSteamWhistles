package net.deano.expanded_steam_whistles.init;

import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AllCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExpandedSteamWhistles.MOD_ID);


    public static final RegistryObject<CreativeModeTab> WHISTLE_TAB = CREATIVE_MODE_TABS.register("whistle_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllItems.WHISTLE.get()))
                    .title(Component.translatable("creativetab.whistle_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(AllItems.WHISTLE.get());
                        pOutput.accept(AllBlocks.CLANK.get());

                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
