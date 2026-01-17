package net.deano.expandedsteamwhistles.init;

import net.deano.expandedsteamwhistles.ExpandedSteamWhistles;
import net.deano.expandedsteamwhistles.whistle.ExpandedSteamWhistleBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExpandedSteamWhistles.MOD_ID);

    public static final RegistryObject<BlockEntityType> EXPANDED_STEAM_WHISTLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("expanded_steam_whistle",
                    () -> BlockEntityType.Builder.of(ExpandedSteamWhistleBlockEntity::new, AllBlocks.EXPANDED_STEAM_WHISTLE.get())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
}
