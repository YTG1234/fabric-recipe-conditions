package io.github.ytg1234.recipeconditions.api.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.ytg1234.recipeconditions.api.RecipeConds;
import io.github.ytg1234.recipeconditions.api.condition.base.RecipeCondition;
import io.github.ytg1234.recipeconditions.api.condition.base.RecipeConditionParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Represents a Json entry that specifies a condition identifier
 * and either one value or an array of them.
 *
 * @author YTG1234
 */
public final class SingleCondition {
    @NotNull
    private final RecipeCondition condition;
    @Nullable
    private final RecipeConditionParameter param;
    @Nullable
    private final DefaultedList<RecipeConditionParameter> params;

    private final boolean negated;

    public SingleCondition(@NotNull RecipeCondition condition, @NotNull RecipeConditionParameter param) {
        this(condition, param, false);
    }

    public SingleCondition(
            @NotNull RecipeCondition condition, @NotNull RecipeConditionParameter param, boolean negated
                          ) {
        this.condition = condition;
        this.param = param;
        this.params = null;
        this.negated = negated;
    }

    public SingleCondition(
            @NotNull RecipeCondition condition, @NotNull DefaultedList<RecipeConditionParameter> params
                          ) {
        this(condition, params, false);
    }

    public SingleCondition(
            @NotNull RecipeCondition condition, @NotNull DefaultedList<RecipeConditionParameter> params, boolean negated
                          ) {
        this.condition = condition;
        this.params = params;
        this.param = null;
        this.negated = negated;
    }

    /**
     * Parses a Json entry to get the condition and value(s) from it.
     *
     * @param entry the Json entry to parse
     *
     * @return the new representation of the entry
     */
    public static SingleCondition fromJson(@NotNull Map.Entry<String, JsonElement> entry) {
        if (entry.getValue().isJsonArray()) {
            DefaultedList<RecipeConditionParameter> values = DefaultedList.of();
            for (JsonElement element : entry.getValue().getAsJsonArray()) {
                values.add(RecipeConditionParameter.fromJsonElement(element));
            }
            boolean negated = false;
            if (entry.getKey().startsWith("!")) negated = true;
            Identifier conditionId = new Identifier(entry.getKey().replace("!", ""));
            RecipeCondition condition = RecipeConds.RECIPE_CONDITION.get(conditionId);
            if (condition == null) {
                throw new JsonParseException(new IllegalArgumentException("Unknown condition " +
                                                                          conditionId.toString() +
                                                                          "!"));
            }
            return new SingleCondition(condition, values, negated);
        } else {
            JsonElement value = entry.getValue();
            boolean negated = false;
            if (entry.getKey().startsWith("!")) negated = true;
            Identifier conditionId = new Identifier(entry.getKey().replace("!", ""));
            RecipeCondition condition = RecipeConds.RECIPE_CONDITION.get(conditionId);
            if (condition == null) {
                throw new JsonParseException(new IllegalArgumentException("Unknown condition " +
                                                                          conditionId.toString() +
                                                                          "!"));
            }
            return new SingleCondition(condition, RecipeConditionParameter.fromJsonElement(value), negated);
        }
    }

    /**
     * A convenient way to check if the internal condition matched for all values.
     *
     * @return whether the condition matched
     */
    public boolean check() {
        if (getParam() != null) {
            return negated != condition.check(param);
        } else if (getParams() != null) {
            return getParams().stream().allMatch(condition::check);
        } else {
            throw new IllegalStateException("How did this happen? params and param are null!");
        }
    }

    @Nullable
    public RecipeConditionParameter getParam() {
        return param;
    }

    @Nullable
    public DefaultedList<RecipeConditionParameter> getParams() {
        return params;
    }

    @NotNull
    public RecipeCondition getCondition() {
        return condition;
    }
}
