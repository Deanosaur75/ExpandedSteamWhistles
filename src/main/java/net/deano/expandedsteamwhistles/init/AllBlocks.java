package net.deano.expandedsteamwhistles.init;

import com.simibubi.create.AllItems;
import net.deano.expandedsteamwhistles.ExpandedSteamWhistles;
import net.deano.expandedsteamwhistles.whistle.ExpandedSteamWhistleBlock;
import net.deano.expandedsteamwhistles.whistle.ExpandedSteamWhistleExtensionBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ExpandedSteamWhistles.MOD_ID);

    public static final RegistryObject<ExpandedSteamWhistleBlock> EXPANDED_STEAM_WHISTLE = registerPipeBlock("gedeckt", "8",
            () -> new ExpandedSteamWhistleBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<ExpandedSteamWhistleExtensionBlock> EXPANDED_STEAM_WHISTLE_EXTENSION_BLOCK = registerBlockWithoutItem("expanded_steam_whistle_extension",
            () -> new ExpandedSteamWhistleExtensionBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)
                    .requiresCorrectToolForDrops()));


    private static <T extends Block> RegistryObject<T> registerPipeBlock(String name, String octave, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerPipeBlockItem(name, toReturn, octave);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> void registerPipeBlockItem(String name, RegistryObject<T> block, String octave) {
        AllItems.ITEMS.register(name, () -> new GenericPipeBlockItem(block.get(), new Item.Properties(), octave));

        public static void register (IEventBus eventBus){
            BLOCKS.register(eventBus);
        }
    }
}
