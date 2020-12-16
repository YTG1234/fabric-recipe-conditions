package io.github.ytg1234.recipeconditions.condition.registry;

import io.github.ytg1234.recipeconditions.RecipeConditionsMod;
import io.github.ytg1234.recipeconditions.condition.RecipeCondition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class RecipeConditions {
    public static final RecipeCondition MOD_LOADED;
    public static final RecipeCondition EMPTY;

    static {
        MOD_LOADED = Registry.register(
                RecipeConditionsRegistries.RECIPE_CONDITION,
                new Identifier(RecipeConditionsMod.MOD_ID, "modloaded"),
                mod -> FabricLoader.getInstance().isModLoaded(mod)
                                      );
        EMPTY = Registry.register(
                RecipeConditionsRegistries.RECIPE_CONDITION,
                new Identifier(RecipeConditionsMod.MOD_ID, "empty"),
                x -> true
                                 );
    }

    private RecipeConditions() {
    }

    public static void register() {
    }
}
