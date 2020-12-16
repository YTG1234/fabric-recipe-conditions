package io.github.ytg1234.recipeconditions;

import io.github.ytg1234.recipeconditions.condition.registry.RecipeConditions;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeConditionsMod implements ModInitializer {
    public static final String MOD_ID = "recipeconditions";
    public static final String MOD_NAME = "Fabric Recipe Conditions";
    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing");
        RecipeConditions.register();
    }
}
