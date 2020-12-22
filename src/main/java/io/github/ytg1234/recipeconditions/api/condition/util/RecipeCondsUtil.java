package io.github.ytg1234.recipeconditions.api.condition.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.ytg1234.recipeconditions.api.condition.base.RecipeCondition;

import java.util.function.Predicate;

public final class RecipeCondsUtil {
    public static RecipeCondition stringParam(Predicate<String> cond) {
        return param -> cond.test(param.string());
    }

    public static RecipeCondition boolParam(Predicate<Boolean> cond) {
        return param -> cond.test(param.bool());
    }

    public static RecipeCondition intParam(Predicate<Integer> cond) {
        return param -> cond.test(param.integer());
    }

    public static RecipeCondition floatParam(Predicate<Float> cond) {
        return param -> cond.test(param.floatingPoint());
    }

    public static RecipeCondition arrayParam(Predicate<JsonArray> cond) {
        return param -> cond.test(param.array());
    }

    public static RecipeCondition objectParam(Predicate<JsonObject> cond) {
        return param -> cond.test(param.object());
    }
}
