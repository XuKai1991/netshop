package com.xukai.netshop.dataobject.mapper;

import com.xukai.netshop.dataobject.SellerInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/25 23:22
 * @modified By:
 */
public interface SellerMapper {

    @Select("select * from seller_info where username=#{username} and password=#{password}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password")
    })
    SellerInfo findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
