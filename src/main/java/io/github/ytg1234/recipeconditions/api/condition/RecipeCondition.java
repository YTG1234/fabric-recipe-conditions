package io.github.ytg1234.recipeconditions.api.condition;

/**
 * Represents a recipe condition that can be registered.
 *
 * @author YTG1234
 */
@FunctionalInterface
public interface RecipeCondition {

    /**
     * Checks if the condition matches a certain value.
     * <p>
     * For exmaple, a condition that would match a mod ID if loaded would look like:
     * <code>
     * modid -> FabricLoader.getInstance().isModLoaded(modid)
     * </code>
     * </p>
     *
     * @param value the value to match against
     *
     * @return if the value matched
     */
    boolean check(String value); // Yes this is basically a Predicate<String>
}
