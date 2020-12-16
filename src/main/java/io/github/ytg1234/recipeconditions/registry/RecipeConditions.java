package io.github.ytg1234.recipeconditions.registry;

import io.github.ytg1234.recipeconditions.RecipeConditionsMod;
import io.github.ytg1234.recipeconditions.api.condition.RecipeCondition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class RecipeConditions {
    public static final RecipeCondition
            MOD_LOADED =
            register("mod_loaded", modid -> FabricLoader.getInstance().isModLoaded(modid));
    public static final RecipeCondition EMPTY = register("empty", x -> true);

    public static final RecipeCondition ITEM_REGISTERED = register("item", Registry.ITEM);
    public static final RecipeCondition BLOCK_REGISTERED = register("block", Registry.BLOCK);
    public static final RecipeCondition ENTITY_TYPE_REGISTERED = register("entity_type", Registry.ENTITY_TYPE);
    public static final RecipeCondition SOUND_EVENT_REGISTERED = register("sound_event", Registry.SOUND_EVENT);
    public static final RecipeCondition FLUID_REGISTERED = register("fluid", Registry.FLUID);
    public static final RecipeCondition STATUS_EFFECT_REGISTERED = register("status_effect", Registry.STATUS_EFFECT);
    public static final RecipeCondition ENCHANTMENT_REGISTERED = register("enchantment", Registry.ENCHANTMENT);
    public static final RecipeCondition
            BLOCK_ENTITY_TYPE_REGISTERED =
            register("block_entity_type", Registry.BLOCK_ENTITY_TYPE);
    public static final RecipeCondition POTION_REGISTERED = register("potion", Registry.POTION);
    public static final RecipeCondition PARTICLE_TYPE_REGISTERED = register("particle_type", Registry.PARTICLE_TYPE);
    public static final RecipeCondition
            PAINTING_MOTIVE_REGISTERED =
            register("painting_motive", Registry.PAINTING_MOTIVE);
    public static final RecipeCondition CUSTOM_STAT_REGISTERED = register("custom_stat", Registry.CUSTOM_STAT);
    public static final RecipeCondition CHUNK_STATUS_REGISTERED = register("chunk_status", Registry.CHUNK_STATUS);
    public static final RecipeCondition RULE_TEST_REGISTERED = register("rule_test", Registry.RULE_TEST);
    public static final RecipeCondition POS_RULE_TEST_REGISTERED = register("pos_rule_test", Registry.POS_RULE_TEST);
    public static final RecipeCondition SCREEN_HANDLER_REGISTERED = register("screen_handler", Registry.SCREEN_HANDLER);
    public static final RecipeCondition RECIPE_TYPE_REGISTERED = register("recipe_type", Registry.RECIPE_TYPE);
    public static final RecipeCondition RECIPE_SERIALIZER_REGISTERED = register("recipe_serializer", Registry.RECIPE_SERIALIZER);
    public static final RecipeCondition ATTRIBUTE_REGISTERED = register("attribute", Registry.ATTRIBUTE);
    public static final RecipeCondition STAT_TYPE_REGISTERED = register("stat_type", Registry.STAT_TYPE);
    public static final RecipeCondition VILLAGER_TYPE_REGISTERED = register("villager_type", Registry.VILLAGER_TYPE);
    public static final RecipeCondition VILLAGER_PROFESSION_REGISTERED = register("villager_profession", Registry.VILLAGER_PROFESSION);
    public static final RecipeCondition POINT_OF_INTEREST_TYPE_REGISTERED = register("point_of_interest_type", Registry.POINT_OF_INTEREST_TYPE);
    public static final RecipeCondition MEMORY_MODULE_TYPE_REGISTERED = register("memory_module_type", Registry.MEMORY_MODULE_TYPE); // I don't even know what this is

    private RecipeConditions() {
    }

    public static void register() {
    }

    private static RecipeCondition register(String id, Registry<?> registry) {
        return register(id + "_registered", x -> registry.getIds().contains(new Identifier(x)));
    }

    private static RecipeCondition register(String id, RecipeCondition cond) {
        return Registry.register(RecipeConditionsRegistries.RECIPE_CONDITION,
                new Identifier(RecipeConditionsMod.MOD_ID, id),
                cond
                                );
    }
}
