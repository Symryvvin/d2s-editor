package ru.aizen.d2.editor.attr;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class AttributeDeserializer implements JsonDeserializer<AttributeSpec> {

    @Override
    public AttributeSpec deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return new AttributeSpec(
                jsonElement.getAsJsonObject().get("id").getAsInt(),
                jsonElement.getAsJsonObject().get("name").getAsString(),
                jsonElement.getAsJsonObject().get("numberOfBits").getAsInt(),
                jsonElement.getAsJsonObject().get("scaleCoefficient").getAsInt()
        );
    }
}
