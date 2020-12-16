package io.github.ytg1234.recipeconditions.mixin;

import com.google.gson.JsonArray;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShapelessRecipe.Serializer.class)
public interface ShapelessRecipe$SerializerAcessor {
    @Invoker("getIngredients")
    static DefaultedList<Ingredient> invokeGetIngredients(JsonArray json) {
        throw null;
    }
}
