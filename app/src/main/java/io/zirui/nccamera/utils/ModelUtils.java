package io.zirui.nccamera.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ModelUtils {
    private static Gson gson = new Gson();
    private static String PREF_NAME = "models";

    private static Gson gsonForSerialization = new GsonBuilder()
            .registerTypeAdapter(Uri.class, new UriSerializer())
            .create();

    private static Gson gsonForDeserialization = new GsonBuilder()
            .registerTypeAdapter(Uri.class, new UriDeserializer())
            .create();


    public static <T> T toObject(String json, TypeToken<T> typeToken) {
        try{
            return gsonForDeserialization.fromJson(json, typeToken.getType());
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toString(T object, TypeToken<T> typeToken) {
        return gsonForSerialization.toJson(object);
    }

    private static class UriSerializer implements JsonSerializer<Uri> {
        @Override
        public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    private static class UriDeserializer implements JsonDeserializer<Uri> {
        @Override
        public Uri deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Uri.parse(json.getAsString());
        }
    }
}
