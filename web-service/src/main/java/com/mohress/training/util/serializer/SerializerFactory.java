package com.mohress.training.util.serializer;

/**
 * @author youtao.wan
 * @date 16-9-6
 */
public class SerializerFactory {

    private static final Serializer DEFAULT = new JacksonSerializer();

    public static Serializer defaultSerializer(){return DEFAULT;}
}
