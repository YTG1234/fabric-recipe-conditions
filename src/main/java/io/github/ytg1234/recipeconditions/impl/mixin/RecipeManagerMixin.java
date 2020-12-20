package io.github.ytg1234.recipeconditions.impl.mixin;

import com.google.gson.JsonElement;
import io.github.ytg1234.recipeconditions.api.condition.AnyCondition;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    private static final Logger logger = LogManager.getLogger(RecipeManagerMixin.class);

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At("HEAD"))
    private void hahaYes(
            Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci
                        ) {
        Iterator<Map.Entry<Identifier, JsonElement>> recipes = map.entrySet().iterator(); // Used to avoid a CME
        while(recipes.hasNext()) {
            Map.Entry<Identifier, JsonElement> entry = recipes.next();
            logger.debug("Attempting to load recipe " + entry.getKey().toString());

            if (entry.getKey().getNamespace().equals("recipeconditions")) {
                logger.debug("Recipe " + entry.getKey().toString() + " is an example recipe, removing.");
                recipes.remove();
                continue;
            }

            JsonElement recipe = entry.getValue();

            if (!recipe.isJsonObject()) continue;
            if (!recipe.getAsJsonObject().has("conditions")) continue;

            logger.debug("Recipe " + entry.getKey().toString() + " has a conditions property");
            AnyCondition
                    conditions =
                    AnyCondition.fromJson(recipe.getAsJsonObject().get("conditions").getAsJsonArray());
            if (!conditions.check()) {
                logger.debug("Conditions didn't match, removing recipe " + entry.getKey().toString());
                recipes.remove();
            }
        }
    }
}
