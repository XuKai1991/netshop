package com.xukai.netshop.dataobject.mapper;

import com.xukai.netshop.dataobject.BuyerInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 21:39
 * @modified By:
 */
public interface BuyerMapper {

    @Insert("insert into buyer_info values(#{buyerId}, #{username}, #{password}, #{phone}, #{email})")
    int register(BuyerInfo buyerInfo);

    @Select("select * from buyer_info where username=#{username} and password=#{password}")
    @Results({
            @Result(column = "buyer_id", property = "buyerId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "email", property = "email")
    })
    BuyerInfo findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from buyer_info where username=#{username}")
    @Results({
            @Result(column = "buyer_id", property = "buyerId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "email", property = "email")
    })
    BuyerInfo checkUsername(@Param("username") String username);

    @Select("select * from buyer_info where email=#{email}")
    @Results({
            @Result(column = "buyer_id", property = "buyerId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "email", property = "email")
    })
    BuyerInfo checkEmail(@Param("email") String email);
}
