package com.pratap.springcloud.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratap.springcloud.model.Coupon;
@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long>{

	Coupon findByCode(String code);

}
