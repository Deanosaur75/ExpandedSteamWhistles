package net.deano.expanded_steam_whistles.datagen;

import net.deano.expanded_steam_whistles.ExpandedSteamWhistles;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;


public class DataGenerators {

    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(ExpandedSteamWhistles.MOD_ID))
            addExtraRegistrateData();
    }

    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(ExpandedSteamWhistles.MOD_ID)) return;

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    }

    private static void addExtraRegistrateData() {
        ExpandedSteamWhistles.registrate().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add; // for every place that generates lang translations, have it provide its lang to the consumer
            provideDefaultLang("en_us_base", langConsumer); // add the entries that already exist
        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/expanded_steam_whistles/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null)
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
        // Mmmmm, yummy Finchy code
    }
}