package com.pratap.springcloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pratap.springcloud.model.Coupon;
import com.pratap.springcloud.repos.CouponRepo;

@RestController
public class CouponRestController {

	@Autowired
	private CouponRepo repository;
	
	@RequestMapping(value = "/coupons", method = RequestMethod.POST)
	public Coupon create(@RequestBody Coupon coupon) {
		return repository.save(coupon);
	}
	
	@RequestMapping(value = "coupons/{code}", method = RequestMethod.GET)
	public Coupon getCoupon(@PathVariable("code") String code) {
		return repository.findByCode(code);
	}
}
