package io.github.ytg1234.recipeconditions.impl.mixin;

import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.ytg1234.recipeconditions.RecipeCondsConstants;
import io.github.ytg1234.recipeconditions.impl.util.ImplUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    private static final Logger logger = LogManager.getLogger(RecipeManagerMixin.class);

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At("HEAD"))
    private void processConditions(
            Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci
                                  ) {
        Iterator<Map.Entry<Identifier, JsonElement>> recipes = map.entrySet().iterator(); // Used to avoid a CME
        while (recipes.hasNext()) {
            Map.Entry<Identifier, JsonElement> entry = recipes.next();
            try {
                if (ImplUtils.shouldRemoveRecipe(entry, RecipeCondsConstants.CONDITIONS_MEMBER)) recipes.remove();
            } catch (JsonParseException e) {
                throw new JsonParseException("Failed to load recipe " + entry.getKey().toString() + "!", e);
            }
        }
    }
}
