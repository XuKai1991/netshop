package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/16 16:19
 * @modified By:
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByUsernameAndPassword(String username, String password);

}
