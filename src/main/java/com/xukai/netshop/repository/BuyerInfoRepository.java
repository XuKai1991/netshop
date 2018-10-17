package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.BuyerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/16 16:19
 * @modified By:
 */
public interface BuyerInfoRepository extends JpaRepository<BuyerInfo, String> {

    BuyerInfo findByUsernameAndPassword(String username, String password);

    BuyerInfo findByUsername(String username);

    BuyerInfo findByEmail(String email);

    Page<BuyerInfo> findByBuyerIdLikeAndUsernameLikeAndPhoneLikeAndEmailLikeOrderByCreateTimeDesc(
            String buyerId, String username, String phone, String email, Pageable pageable
    );
}
