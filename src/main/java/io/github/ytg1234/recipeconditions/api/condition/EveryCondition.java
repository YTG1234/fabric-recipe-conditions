package io.github.ytg1234.recipeconditions.api.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a list of {@link SingleCondition}s, that all
 * of them have to match for this list to match.
 *
 * @author YTG1234
 */
public final class EveryCondition {
    @NotNull
    private final DefaultedList<SingleCondition> conditions;

    public EveryCondition(@NotNull DefaultedList<SingleCondition> conditions) {
        this.conditions = conditions;
    }

    /**
     * Parses a Json object to a list of {@link SingleCondition EntryInRecipes}.
     *
     * @param object the object to be parsed
     *
     * @return the parsed representation
     */
    public static EveryCondition fromJson(JsonObject object) {
        DefaultedList<SingleCondition> list = DefaultedList.of();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            list.add(SingleCondition.fromJson(entry));
        }
        return new EveryCondition(list);
    }

    /**
     * Checks if all the conditions in the list match.
     *
     * @return whether they match
     */
    public boolean check() {
        return getConditions().stream().allMatch(SingleCondition::check);
    }

    @NotNull
    public DefaultedList<SingleCondition> getConditions() {
        return conditions;
    }
}
