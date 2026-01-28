package net.deano.expanded_steam_whistles;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.deano.expanded_steam_whistles.init.*;
import net.deano.expanded_steam_whistles.datagen.DataGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExpandedSteamWhistles.MOD_ID)
public class ExpandedSteamWhistles
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "expanded_steam_whistles";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );


    public ExpandedSteamWhistles(IEventBus modEventBus, ModContainer container)
    {

        REGISTRATE.registerEventListeners(modEventBus);

        AllCreativeModeTabs.register(modEventBus);

        AllSoundEvents.register(modEventBus);

        AllBlocks.register();
        AllPartialModels.init();
        AllTags.init();

        AllBlockEntityTypes.register();

        modEventBus.addListener(EventPriority.HIGHEST, DataGenerators::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, DataGenerators::gatherData);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
}