package io.github.ytg1234.recipeconditions.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.ytg1234.recipeconditions.Utils;
import io.github.ytg1234.recipeconditions.condition.AnyCondition;
import io.github.ytg1234.recipeconditions.mixin.ShapelessRecipe$SerializerAcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class ShapelessConditionalRecipe extends ShapelessRecipe {
    private final AnyCondition conditions;

    public ShapelessConditionalRecipe(
            Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input, AnyCondition conditions
                                     ) {
        super(id, group, output, input);
        this.conditions = conditions;
    }

    public AnyCondition getConditions() {
        return conditions;
    }

    public static class Serializer implements RecipeSerializer<ShapelessConditionalRecipe> {
        @Override
        public ShapelessConditionalRecipe read(Identifier id, JsonObject recipe) {
            String group = JsonHelper.getString(recipe, "group", "");
            DefaultedList<Ingredient>
                    ingredients =
                    ShapelessRecipe$SerializerAcessor.invokeGetIngredients(JsonHelper.getArray(recipe, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            } else {
                ItemStack result = ShapedRecipe.getItemStack(JsonHelper.getObject(recipe, "result"));
                AnyCondition conditions = AnyCondition.fromJson(JsonHelper.getArray(recipe, "conditions"));
                return new ShapelessConditionalRecipe(id, group, result, ingredients, conditions);
            }
        }

        @Override
        public ShapelessConditionalRecipe read(Identifier id, PacketByteBuf recipeBuf) {
            String group = recipeBuf.readString(32767);
            int ingredientCount = recipeBuf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(ingredientCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.fromPacket(recipeBuf));
            }
            ItemStack output = recipeBuf.readItemStack();
            AnyCondition conditions = Utils.readArrayInRecipe(recipeBuf);
            return new ShapelessConditionalRecipe(id, group, output, ingredients, conditions);
        }

        @Override
        public void write(PacketByteBuf recipeBuf, ShapelessConditionalRecipe recipe) {
            recipeBuf.writeString(recipe.getGroup());
            recipeBuf.writeVarInt(recipe.getPreviewInputs().size());

            for (Ingredient ingredient : recipe.getPreviewInputs()) {
                ingredient.write(recipeBuf);
            }

            recipeBuf.writeItemStack(recipe.getOutput());
            Utils.writeArrayInRecipe(recipeBuf, recipe.getConditions());
        }
    }
}
