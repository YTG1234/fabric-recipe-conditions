package io.github.ytg1234.recipeconditions.registry;

import io.github.ytg1234.recipeconditions.RecipeConditionsMod;
import io.github.ytg1234.recipeconditions.api.condition.RecipeCondition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class RecipeConditions {
    public static final RecipeCondition MOD_LOADED;
    public static final RecipeCondition EMPTY;

    public static final RecipeCondition ITEM_REGISTERED;
    public static final RecipeCondition BLOCK_REGISTERED;
    public static final RecipeCondition ENTITY_TYPE_REGISTERED;
    public static final RecipeCondition SOUND_EVENT_REGISTERED;
    public static final RecipeCondition FLUID_REGISTERED;
    public static final RecipeCondition STATUS_EFFECT_REGISTERED;
    public static final RecipeCondition ENCHANTMENT_REGISTERED;
    public static final RecipeCondition BLOCK_ENTITY_TYPE_REGISTERED;


    static {
        MOD_LOADED = register("mod_loaded", modid -> FabricLoader.getInstance().isModLoaded(modid));
        EMPTY = register("empty", x -> true);

        ITEM_REGISTERED = register("item", Registry.ITEM);
        BLOCK_REGISTERED = register("block", Registry.BLOCK);
        ENTITY_TYPE_REGISTERED = register("entity_type", Registry.ENTITY_TYPE);
        SOUND_EVENT_REGISTERED = register("sound_event", Registry.SOUND_EVENT);
        FLUID_REGISTERED = register("fluid", Registry.FLUID);
        STATUS_EFFECT_REGISTERED = register("status_effect", Registry.STATUS_EFFECT);
        ENCHANTMENT_REGISTERED = register("enchantment", Registry.ENCHANTMENT);
        BLOCK_ENTITY_TYPE_REGISTERED = register("block_entity_type", Registry.BLOCK_ENTITY_TYPE);
    }

    private RecipeConditions() {
    }

    public static void register() {
    }

    private static RecipeCondition register(String id, Registry<?> registry) {
        return register(id + "_registered", x -> registry.containsId(new Identifier(x)));
    }

    private static RecipeCondition register(String id, RecipeCondition cond) {
        return Registry.register(RecipeConditionsRegistries.RECIPE_CONDITION,
                new Identifier(RecipeConditionsMod.MOD_ID, id),
                cond
                                );
    }
}
