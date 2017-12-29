package com.dangdailife.networks.network.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author Joy Wong
 * @description json数据处理工具类
 * @datetime 2016/06/22 18:30
 * @email wjb18814888154@gmail.com
 */
public class JsonUtil {
    private static Gson gson=new GsonBuilder().serializeNulls().create();
    private static JsonParser jsonParser=new JsonParser();

    /**
     *把对象序列化为json字符串
     * @param object 对象实体
     * @param <T>类
     * @return json字符串
     */
    public static <T> String serialize(T object){
        return  gson.toJson(object);
    }

    /**
     * 把对象序列化为JsonObject对象
     * @param object
     * @param <T>
     * @return
     */
    public static <T> JsonObject serializeToJsonObject(T object){
        return  gson.toJsonTree(object).getAsJsonObject();
    }

    /**
     *
     * @param json json字符串
     * @param clz 类型信息
     * @param <T>类
     * @return 对象
     */
    public static <T> T deserialize(String json, Class<T> clz){
        return gson.fromJson(json,clz);
    }


    /**
     *
     * @param jsonElement an element of Json
     * @param clz 类型信息
     * @param <T>类
     * @return 对象
     */
    public static <T> T deserialize(JsonElement jsonElement, Class<T> clz){
        return gson.fromJson(jsonElement,clz);
    }


    /**
     *
     * @param jsonElement an element of Json
     * @param type 类型信息
     * @param <T>类
     * @return 对象
     */
    public static <T> T deserialize(JsonElement jsonElement, Type type){
        return gson.fromJson(jsonElement,type);
    }

    /**
     *
     * @param type 类型信息
     * @param <T>类
     * @return 对象
     */
    public static <T> T deserialize(Reader reader, Type type){
        return gson.fromJson(reader,type);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type type){
        return gson.fromJson(json,type);
    }

    /**
     * 把字符串解析为JsonElement对象
     * @param json
     * @return
     */
    private static JsonElement toJsonElement(String json){
        return jsonParser.parse(json);
    }

    /**
     * 把字符串解析为JsonObject对象
     * @param json
     * @return
     */
    public static JsonObject toJsonObject(String json){
        return toJsonElement(json).getAsJsonObject();
    }

    /**
     * 把字符串解析为JsonArray对象
     * @param json
     * @return
     */
    public static JsonArray toJsonArray(String json){
        return toJsonElement(json).getAsJsonArray();
    }
}
