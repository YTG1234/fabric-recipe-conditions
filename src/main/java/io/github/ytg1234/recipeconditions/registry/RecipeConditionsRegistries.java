package io.github.ytg1234.recipeconditions.registry;

import io.github.ytg1234.recipeconditions.RecipeConditionsMod;
import io.github.ytg1234.recipeconditions.api.condition.RecipeCondition;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class RecipeConditionsRegistries {
    public static final Registry<RecipeCondition>
            RECIPE_CONDITION =
            FabricRegistryBuilder.createSimple(RecipeCondition.class,
                    new Identifier(RecipeConditionsMod.MOD_ID, "recipe_condition")
                                              )
                    .attribute(RegistryAttribute.SYNCED)
                    .buildAndRegister();

    private RecipeConditionsRegistries() {
    }
}
