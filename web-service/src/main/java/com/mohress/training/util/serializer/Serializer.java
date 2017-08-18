package com.mohress.training.util.serializer;

/**
 * @author youtao.wan
 * @date 16-9-5
 */
public interface Serializer {

    String serialize(Object data);

    <T> T deserialize(String content, Class<T> clazz);
}
