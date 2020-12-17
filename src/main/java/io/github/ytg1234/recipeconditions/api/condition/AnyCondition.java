package io.github.ytg1234.recipeconditions.api.condition;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an array of {@link EveryCondition}s - The highest level
 * of condition representation.
 *
 * @author YTG1234
 */
public final class AnyCondition {
    /**
     * The internal array of {@link EveryCondition}s, which is used when
     * matching.
     */
    @NotNull
    private final DefaultedList<EveryCondition> conditions;

    public AnyCondition(@NotNull DefaultedList<EveryCondition> conditions) {
        this.conditions = conditions;
    }

    /**
     * Parses a Json array into a simple list of {@link EveryCondition ObjectInRecipes}.
     *
     * @param array the array to be parsed
     *
     * @return the parsed form of the array
     */
    public static AnyCondition fromJson(@NotNull JsonArray array) {
        DefaultedList<EveryCondition> list = DefaultedList.of();
        for (JsonElement element : array) {
            if (!(element instanceof JsonObject)) throw new JsonParseException("Conditions must be objects!");
            list.add(EveryCondition.fromJson((JsonObject) element));
        }
        return new AnyCondition(list);
    }

    @NotNull
    public DefaultedList<EveryCondition> getConditions() {
        return conditions;
    }

    /**
     * Checks if the array matches - if any of its elements match.
     *
     * @return whether the array matches
     */
    public boolean check() {
        return getConditions().stream().anyMatch(EveryCondition::check);
    }
}
