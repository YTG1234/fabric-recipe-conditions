package io.github.ytg1234.recipeconditions;

import io.github.ytg1234.recipeconditions.condition.AnyCondition;
import io.github.ytg1234.recipeconditions.condition.EveryCondition;
import io.github.ytg1234.recipeconditions.condition.SingleCondition;
import io.github.ytg1234.recipeconditions.condition.registry.RecipeConditionsRegistries;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public final class Utils {
    public static void writeArrayInRecipe(PacketByteBuf target, AnyCondition toWrite) {
        target.writeVarInt(toWrite.getConditions().size());
        for (EveryCondition object : toWrite.getConditions()) {
            writeObjectInRecipe(target, object);
        }
    }

    public static void writeObjectInRecipe(PacketByteBuf target, EveryCondition toWrite) {
        target.writeVarInt(toWrite.getConditions().size());
        for (SingleCondition entry : toWrite.getConditions()) {
            writeEntryInRecipe(target, entry);
        }
    }

    public static void writeEntryInRecipe(PacketByteBuf target, SingleCondition toWrite) {
        target.writeIdentifier(RecipeConditionsRegistries.RECIPE_CONDITION.getId(toWrite.getCondition()));
        target.writeString(toWrite.getValue());
    }

    public static AnyCondition readArrayInRecipe(PacketByteBuf from) {
        int objectAmount = from.readVarInt();
        DefaultedList<EveryCondition> objects = DefaultedList.ofSize(objectAmount, EveryCondition.EMPTY);
        for (int i = 0; i < objectAmount; i++) {
            objects.set(i, readObjectInRecipe(from));
        }
        return new AnyCondition(objects);
    }

    public static EveryCondition readObjectInRecipe(PacketByteBuf from) {
        int entryAmount = from.readVarInt();
        DefaultedList<SingleCondition> entries = DefaultedList.ofSize(entryAmount, SingleCondition.EMPTY);
        for (int i = 0; i < entryAmount; i++) {
            entries.set(i, readEntryInRecipe(from));
        }
        return new EveryCondition(entries);
    }

    public static SingleCondition readEntryInRecipe(PacketByteBuf from) {
        Identifier condId = from.readIdentifier();
        String value = from.readString();
        return new SingleCondition(RecipeConditionsRegistries.RECIPE_CONDITION.get(condId), value);
    }
}
