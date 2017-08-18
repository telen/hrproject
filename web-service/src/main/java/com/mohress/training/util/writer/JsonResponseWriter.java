package com.mohress.training.util.writer;

import com.mohress.training.util.serializer.SerializerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by youtao.wan on 2017/8/17.
 */
@Slf4j
@AllArgsConstructor
public class JsonResponseWriter implements Writer {

    private HttpServletResponse response;

    @Override
    public <T> void write(T data) {
        if (response == null || response.isCommitted()){
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        String json = SerializerFactory.defaultSerializer().serialize(data);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.append(json);
        }catch (IOException ie){
            log.error("Http响应流写入数据IO异常。 data={}", data);
        }finally {
            if (writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
