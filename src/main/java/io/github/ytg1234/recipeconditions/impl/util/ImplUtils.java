package io.github.ytg1234.recipeconditions.impl.util;

import java.util.Map;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.ytg1234.recipeconditions.RecipeCondsConstants;
import io.github.ytg1234.recipeconditions.api.condition.AnyCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.util.version.VersionPredicateParser;

public final class ImplUtils {
    private static final Logger logger = LogManager.getLogger(ImplUtils.class);

    public static boolean modVersionLoaded(String id, String requiredVersion) {
        Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(id);
        if (mod.isPresent()) {
            Version version = mod.get().getMetadata().getVersion();
            RecipeCondsConstants.LOGGER.debug(version.toString());
            try {
                return VersionPredicateParser.matches(version, requiredVersion);
            } catch (VersionParsingException e) {
                throw new RuntimeException(e); // The solution to checked exceptions
            }
        }
        return false;
    }

    /**
     * Checks a recipe for conditions, processes them and decides whether to remove the recipe.
     *
     * @param entry the recipe to be processed
     *
     * @return true if the recipe should be removed, false otherwise
     *
     * @throws JsonParseException if the recipe has a conditions property but parsing it failed
     */
    public static boolean shouldRemoveRecipe(Map.Entry<Identifier, JsonElement> entry, String conditionsMember) throws JsonParseException {
        logger.info("Attempting to load recipe " + entry.getKey().toString());

        JsonElement recipe = entry.getValue();

        if (!recipe.isJsonObject()) return false;
        if (!recipe.getAsJsonObject().has(conditionsMember)) return false;

        logger.debug("Recipe " + entry.getKey().toString() + " has a " + conditionsMember + " property.");
        AnyCondition conditions = AnyCondition.fromJson(recipe.getAsJsonObject().get(conditionsMember).getAsJsonArray());
        if (!conditions.check()) {
            logger.debug("Conditions didn't match, removing recipe " + entry.getKey().toString());
            return true;
        }

        return false;
    }
}
