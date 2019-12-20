package com.lvhaifeng.cloud.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.lvhaifeng.cloud.common.constant.DateFormatters;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Description 时间格式工具类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:46
 */
public class JsonUtils {
    private static ObjectMapper objectMapper;
    static {
        objectMapper = newObjectMapper();
    }
    public static ObjectMapper newObjectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateFormatters.STANDARD_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateFormatters.STANDARD_DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateFormatters.STANDARD_TIME_FORMATTER));
        // deserializer
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateFormatters.STANDARD_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateFormatters.STANDARD_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateFormatters.STANDARD_TIME_FORMATTER));
        javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
        return objectMapper;
    }

    public static String toJSONString(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("toJSONSting error", e);
        }
    }

    public static <T> T parseObject(String jsonSting, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonSting, clazz);
        } catch (IOException e) {
            throw new RuntimeException("parseObject error", e);
        }
    }
}
