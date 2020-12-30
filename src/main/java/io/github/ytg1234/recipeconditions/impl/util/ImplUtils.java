package io.github.ytg1234.recipeconditions.impl.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    private static final String CONDITIONS_MEMBER = "frc:conditions";
    private static final Logger logger = LogManager.getLogger("Recipe Conditions");

    /**
     * Checks if a mod is loaded and matches a specific version range.
     *
     * @param id              the id of the mod to be checked
     * @param requiredVersion the version or range to check for
     *
     * @return whether the requirement was satisfied
     */
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

    public static void tryLoadRecipes(Map<Identifier, JsonElement> map) {
        Iterator<Map.Entry<Identifier, JsonElement>> recipes = map.entrySet().iterator(); // Used to avoid a CME

        while (recipes.hasNext()) {
            Map.Entry<Identifier, JsonElement> entry = recipes.next();
            logger.info("Attempting to load recipe " + entry.getKey().toString());

            checkForForgeConditionRecipe(entry);

            try {
                if (shouldRemoveRecipe(entry)) recipes.remove();
            } catch (JsonParseException e) {
                throw new JsonParseException("Failed to load recipe " + entry.getKey().toString() + "!", e);
            }
        }
    }

    private static void checkForForgeConditionRecipe(Map.Entry<Identifier, JsonElement> recipe) {
        if (!recipe.getValue().isJsonObject()) return;
        if (recipeUsesForgeStyleConditions(recipe.getKey(), recipe.getValue().getAsJsonObject())) {
            logger.warn("Recipe " +
                        recipe.getKey().toString() +
                        " is using a \"conditions\" property and is not a Forge mod. Consider switching to FRC?");
        }
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
    private static boolean shouldRemoveRecipe(Map.Entry<Identifier, JsonElement> entry) throws JsonParseException {
        JsonElement recipe = entry.getValue();

        if (!recipe.isJsonObject()) return false;
        if (!recipe.getAsJsonObject().has(ImplUtils.CONDITIONS_MEMBER)) return false;

        logger.debug("Recipe " + entry.getKey().toString() + " has a " + ImplUtils.CONDITIONS_MEMBER + " property.");
        AnyCondition conditions = AnyCondition.fromJson(recipe.getAsJsonObject().get(ImplUtils.CONDITIONS_MEMBER).getAsJsonArray());
        if (!conditions.check()) {
            logger.debug("Conditions didn't match, removing recipe " + entry.getKey().toString());
            return true;
        }

        return false;
    }

    /**
     * Checks if a recipe has a {@code "conditions"} parameter and was
     * not patched using Patchwork.
     *
     * @param recipe the ID to check, body won't be enough
     * @param body   the recipe {@link JsonObject}
     *
     * @return whether the recipe belongs to a non-Forge mod and is still using the Forge-style conditions
     */
    private static boolean recipeUsesForgeStyleConditions(Identifier recipe, JsonObject body) {
        return body.has("conditions") && !isForgeMod(recipe.getNamespace());
    }

    /**
     * Checks if a mod of a certain ID
     * was patched using Patchwork.
     *
     * @param id the id of the mod
     *
     * @return whether it was patched with Patchwork
     */
    private static boolean isForgeMod(String id) {
        Optional<ModContainer> modAccess = FabricLoader.getInstance().getModContainer(id);
        if (!modAccess.isPresent()) return false;
        ModContainer mod = modAccess.get();
        return mod.getMetadata().containsCustomValue("patchwork:patcherMeta");
    }
}
