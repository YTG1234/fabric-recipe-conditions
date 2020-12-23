package io.github.ytg1234.recipeconditions.impl.util;

import io.github.ytg1234.recipeconditions.RecipeCondsConstants;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.util.version.VersionPredicateParser;

import java.util.Optional;

public final class ImplUtils {
    public static boolean modVersionLoaded(String id, String requiredVersion) {
        Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(id);
        if (mod.isPresent()) {
            Version version = mod.get().getMetadata().getVersion();
            RecipeCondsConstants.LOGGER.debug(version.toString());
            try {
                return VersionPredicateParser.matches(version, requiredVersion);
            } catch (VersionParsingException e) {
                throw new RuntimeException(e); // The solution to checked exceptions
            }
        }
        return false;
    }
}
