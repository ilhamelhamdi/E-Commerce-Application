package com.app.services;

import com.app.payloads.CouponDTO;

import java.util.List;

public interface CouponService {
    CouponDTO addCoupon(CouponDTO couponDTO);
    List<CouponDTO> getAllCoupons();
}