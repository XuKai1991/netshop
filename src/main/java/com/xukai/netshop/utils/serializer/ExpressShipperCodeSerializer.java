package com.xukai.netshop.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xukai.netshop.enums.ExpressShipperEnum;
import com.xukai.netshop.utils.EnumUtils;

import java.io.IOException;

/**
 * @author: Xukai
 * @description: ExpressInfo返回Json时直接返回快递公司名称
 * @createDate: 2018/11/8 14:27
 * @modified By:
 */
public class ExpressShipperCodeSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(EnumUtils.getMsgByCode(value, ExpressShipperEnum.class).getMessage());
    }
}
