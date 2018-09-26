package com.xukai.netshop.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/28 1:15
 * Modified By:
 */
public class Fen2YuanSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer fen, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeNumber(new BigDecimal(fen).movePointLeft(2).doubleValue());
    }
}
