package io.github.ytg1234.recipeconditions.api.condition.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public final class RecipeConditionParameter {
    @NotNull
    private final JsonElement value;

    public RecipeConditionParameter(@NotNull JsonElement json) {
        this.value = json;
    }

    @NotNull
    public static RecipeConditionParameter fromJsonElement(@NotNull JsonElement json) {
        return new RecipeConditionParameter(json);
    }

    @NotNull
    public static RecipeConditionParameter fromString(@NotNull String string) {
        return fromJsonElement(new JsonPrimitive(string));
    }

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
