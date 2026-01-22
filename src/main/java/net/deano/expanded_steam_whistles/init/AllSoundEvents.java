package net.deano.expanded_steam_whistles.init;

import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ExpandedSteamWhistles.MOD_ID);



    public static final DeferredHolder<SoundEvent, SoundEvent>
            EXPANDED_WHISTLE_SUPERHIGH = registerSoundEvents("expanded_steam_whistle_superhigh"),
            EXPANDED_WHISTLE_HIGH = registerSoundEvents("expanded_steam_whistle_high"),
            EXPANDED_WHISTLE_MEDIUM = registerSoundEvents("expanded_steam_whistle_medium"),
            EXPANDED_WHISTLE_LOW = registerSoundEvents("expanded_steam_whistle_low"),
            EXPANDED_WHISTLE_DEEP = registerSoundEvents("expanded_steam_whistle_deep"),
            WHISTLE_CHIFF = registerSoundEvents("expanded_whistle_chiff");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(java.lang.String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent
                (ResourceLocation.fromNamespaceAndPath
                        (ExpandedSteamWhistles.MOD_ID, name)));
    }

    public static void register(IEventBus eventbus) {
        SOUND_EVENTS.register(eventbus);
    }
}
