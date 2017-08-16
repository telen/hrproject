package com.mohress.training.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

/**
 * Created by huangfeng.huang on 2016/5/18.
 */
public class JsonUtil {
    private static final class JsonUtilContainer {
        private static final JsonUtil instances = new JsonUtil();
    }

    public static ObjectMapper objectMapper;

    static {
        objectMapper = generateMapper();
    }

    public static JsonUtil getInstance() {
        return JsonUtilContainer.instances;
    }

    private JsonUtil() {
    }

    /**
     * @return 返回ObjectMapper对象
     */
    private static ObjectMapper generateMapper() {

        ObjectMapper customMapper = new ObjectMapper();

        // 设置输出时包含属性的风格
        //  customMapper.setSerializationInclusion(inclusion);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*
        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        customMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);// 取消默认转换timestamps形式;
          */
        return customMapper;
    }

    public <T> T convertToBean(Class<T> clazz, String jsonstr) throws Exception {
        if (Strings.isNullOrEmpty(jsonstr)) {
            return null;
        }

        return objectMapper.readValue(jsonstr, clazz);
    }

    public <T> T[] convertToArray(Class<T[]> clazz, String jsonstr) throws Exception {
        if (Strings.isNullOrEmpty(jsonstr)) {
            return null;
        }

        return objectMapper.readValue(jsonstr, clazz);
    }

    public String beanToText(Object bean) throws Exception {
        if (bean == null) {
            return null;
        }
        if (bean instanceof String) {
            return (String) bean;
        }
        return objectMapper.writeValueAsString(bean);
    }
}
