package com.github.novel.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper;

    private JsonUtils() {
    }

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            T object = objectMapper.readValue(json, clazz);
            return object;
        } catch (IOException e) {
            LOGGER.error("JsonUtils.json2Object  json = " + json, e);
            return null;
        }
    }

    public static String object2Json(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            LOGGER.error("JsonUtils.object2Json occured:", e);
            return null;
        }
    }

    public static <T> List<T> json2List(String json, Class<T> elementClasses) {
        return json2List(json, List.class, elementClasses);
    }

    /**
     * @param json            json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    public static <T> List<T> json2List(String json, Class<? extends List> collectionClass, Class<T> elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("JsonUtils.json2List  json={} ", json, e);
            return null;
        }
    }

    /**
     * @param json
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) {
        return json2Map(json, Map.class, keyClass, valueClass);

    }

    /**
     * @param json
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("JsonUtils.json2Map  json={} ", json, e);
            return null;
        }

    }

}
