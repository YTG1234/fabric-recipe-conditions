package io.github.ytg1234.recipeconditions.testmod;

import io.github.ytg1234.recipeconditions.api.RecipeConds;
import io.github.ytg1234.recipeconditions.api.condition.util.RecipeCondsUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMod implements ModInitializer {
    public static Logger logger = LogManager.getLogger("TestMod");

    @Override
    public void onInitialize() {
        logger.info("Test mod onInitialize");
        Registry.register(RecipeConds.RECIPE_CONDITION, id("param"), RecipeCondsUtil.boolParam(param -> param));
        Registry.register(RecipeConds.RECIPE_CONDITION, id("log_array"), RecipeCondsUtil.arrayParam(arr -> {
            logger.info(arr.toString());
            return true;
        }));
    }

    public static Identifier id(String path) {
        return new Identifier("testmod", path);
    }
}
