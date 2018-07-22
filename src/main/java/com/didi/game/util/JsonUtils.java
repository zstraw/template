package com.didi.game.util;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class JsonUtils {

    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    /**
     * 初始化ObjectMapper
     *
     * @return
     */
    private static ObjectMapper createObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

//        objectMapper.registerModule(new Hibernate4Module().enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING));
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    public static String object2Json(Object o) {
        return JSON.toJSONString(o);
    }

    public static Map<String, Object> object2Map(Object o) {
        return OBJECT_MAPPER.convertValue(o, Map.class);
    }


    /**
     * 将json 字段串转换为对象.
     *
     * @param json  字符串
     * @param clazz 需要转换为的类
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将 json 字段串转换为 List.
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 将 json 字段串转换为 List.
     */
    public static <T> List<T> json2List(List<Map<String, String>> jsonMaps, Class<T> clazz) throws Exception {
        if (jsonMaps == null) {
            return new ArrayList<>();
        }
        return jsonMaps.stream().map(m -> {
            try {
                return mapToObject(m, clazz);
            } catch (Exception e) {
                throw new RuntimeException("json解析异常");
            }
        }).collect(Collectors.toList());
    }

    private static <T> T mapToObject(Map<String, String> map, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        if (CollectionUtils.isEmpty(map)) {
            return t;
        }
        for (Field field : fields) {
            StringBuilder name = new StringBuilder(field.getName());
            String strName = name.toString();
            if ("serialVersionUID".equals(strName)) {
                break;
            }
            name = name.replace(0, 1, (name.charAt(0) + "").toUpperCase());
            Class clz = field.getType();
            Method method = clazz.getMethod("set" + name, clz);
            method.invoke(t, map.get(strName));
        }
        return t;
    }

    /**
     * 将 json 字段串转换为 数据.
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);

    }

    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            T t = OBJECT_MAPPER.treeToValue(jsonNode, clazz);
            return t;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), e);
        }
    }

    public static JsonNode object2Node(Object o) {
        try {
            if (o == null) {
                return OBJECT_MAPPER.createObjectNode();
            } else {
                return OBJECT_MAPPER.convertValue(o, JsonNode.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
    }

}
