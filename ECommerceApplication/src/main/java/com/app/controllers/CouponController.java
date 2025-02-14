package com.app.controllers;

import com.app.payloads.*;
import com.app.services.CouponService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/admin/add-coupon")
    public ResponseEntity<CouponDTO> addCoupon(@RequestBody CouponDTO couponDTO) {
        CouponDTO coupon = couponService.addCoupon(couponDTO);

        return new ResponseEntity<CouponDTO>(coupon, HttpStatus.CREATED);
    }


    @GetMapping("/public/coupons")
    public ResponseEntity<List<CouponDTO>> getCoupons() {
        List<CouponDTO> couponDTOS = couponService.getAllCoupons();
        return new ResponseEntity<List<CouponDTO>>(couponDTOS, HttpStatus.FOUND);
    }
}