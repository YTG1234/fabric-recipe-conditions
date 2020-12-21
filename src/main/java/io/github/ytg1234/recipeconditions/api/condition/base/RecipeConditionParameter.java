package io.github.ytg1234.recipeconditions.api.condition.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a parameter that can be fed into a {@link RecipeCondition}
 * to later match.
 * <p>
 * It contains some helper methods as well.
 * </p>
 *
 * @author YTG1234
 */
public final class RecipeConditionParameter {
    @NotNull
    private final JsonElement value;

    public RecipeConditionParameter(@NotNull JsonElement json) {
        this.value = json;
    }

    /**
     * A utility method, shortcut, to create a new
     * instance using a {@link JsonPrimitive}.
     *
     * @param string string that is the param
     *
     * @return new parameter instance
     */
    public static RecipeConditionParameter fromString(@NotNull String string) {
        return fromJsonElement(new JsonPrimitive(string));
    }

    /**
     * I don't know why I made this, it's actually <em>longer</em> than
     * calling the constructor, but I made it anyway.
     *
     * @param json the param's internal value
     *
     * @return new parameter instance
     */
    public static RecipeConditionParameter fromJsonElement(@NotNull JsonElement json) {
        return new RecipeConditionParameter(json);
    }

    /**
     * A utility method, shortcut, to create a new
     * instance using a {@link JsonPrimitive}.
     *
     * @param i integer that is the param
     *
     * @return new parameter instance.
     */
    public static RecipeConditionParameter fromInt(int i) {
        return fromJsonElement(new JsonPrimitive(i));
    }

    @NotNull
    public JsonElement getValue() {
        return value;
    }

    public String string() {
        return value.getAsString();
    }

    public int integer() {
        return value.getAsInt();
    }

    public double doublePersicion() {
        return value.getAsDouble();
    }

    public float floatingPoint() {
        return value.getAsFloat();
    }

    public JsonObject object() {
        return value.getAsJsonObject();
    }
}
