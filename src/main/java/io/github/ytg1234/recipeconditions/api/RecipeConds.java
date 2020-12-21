package io.github.ytg1234.recipeconditions.api;

import io.github.ytg1234.recipeconditions.RecipeCondsConstants;
import io.github.ytg1234.recipeconditions.api.condition.base.RecipeCondition;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class RecipeConds {
    public static final Registry<RecipeCondition>
            RECIPE_CONDITION =
            FabricRegistryBuilder.createSimple(
                    RecipeCondition.class,
                    new Identifier(RecipeCondsConstants.MOD_ID, "recipe_condition")
                                              ).buildAndRegister();

    private RecipeConds() {
    }
}
