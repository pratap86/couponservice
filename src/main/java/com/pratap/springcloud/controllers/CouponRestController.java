package com.pratap.springcloud.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratap.springcloud.exceptions.CouponNotFoundException;
import com.pratap.springcloud.model.Coupon;
import com.pratap.springcloud.repos.CouponRepo;

@RestController
public class CouponRestController {

	@Autowired
	private CouponRepo repository;
	
	@GetMapping("/coupons")
	public List<Coupon> getCoupons(){
		List<Coupon> coupons = repository.findAll();
		if(coupons.isEmpty()) {
			throw new CouponNotFoundException("Coupns are not available");
		}
		return coupons;
	}

	@RequestMapping(value = "/coupons", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@Valid @RequestBody Coupon coupon) {
		Coupon savedCoupon = repository.save(coupon);
		// return proper status - 201 created & where the new resource is created get
		// URI
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedCoupon.getId())
						.toUri();

		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/coupons/search", method = RequestMethod.GET)
	public Coupon getCouponByCode(@RequestParam String code) {

		Coupon coupon = repository.findByCode(code);
		if (coupon == null) {
			throw new CouponNotFoundException("code : " + code);
		}
		return coupon;
	}
	
	@RequestMapping(value = "coupons/{id}", method = RequestMethod.GET)
	public Coupon getCouponById(@PathVariable("id") long id) {

		Optional<Coupon> optionalCoupon = repository.findById(id);
		if (!optionalCoupon.isPresent()) {
			throw new CouponNotFoundException("id : " + id);
		}
		return optionalCoupon.get();
	}
	
	@DeleteMapping("coupons/{id}")
	public void deleteCoupon(@PathVariable long id) {
		if(! repository.findById(id).isPresent()) {
			throw new CouponNotFoundException("Not available resource Id :"+id);
		}
		repository.deleteById(id);
	}
	
	@PutMapping("coupons/{id}")
	public Coupon updateCompleteResource(@RequestBody Coupon coupon, @PathVariable("id") long id){
		Coupon savedCoupon = repository.findById(id).get();
		if(savedCoupon == null) {
			throw new CouponNotFoundException("Not available resource Id :"+id);
		}
		savedCoupon.setCode(coupon.getCode());
		savedCoupon.setDiscount(coupon.getDiscount());
		savedCoupon.setExpDate(coupon.getExpDate());
		return repository.save(savedCoupon);
	}
	
	@PatchMapping("coupons/{id}")
	public Coupon updatePartialResource(@RequestBody Coupon coupon, @PathVariable("id") long id){
		Coupon savedCoupon = repository.findById(id).get();
		if(savedCoupon == null) {
			throw new CouponNotFoundException("Not available resource Id :"+id);
		}
		savedCoupon.setCode(coupon.getCode());
		return repository.save(savedCoupon);
	}
	
	
}
