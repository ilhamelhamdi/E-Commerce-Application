package com.app.services;

import com.app.entites.Coupon;
import com.app.exceptions.APIException;
import com.app.payloads.*;
import com.app.repositories.CouponRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public CouponDTO addCoupon(CouponDTO couponDTO) {
        Coupon coupon = new Coupon();
        coupon.setDiscount(couponDTO.getDiscount());
        coupon.setCouponName(couponDTO.getCouponName());

        Coupon savedCoupon = couponRepo.save(coupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public List<CouponDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepo.findAll();

        return coupons.stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class))
                .collect(Collectors.toList());
    }
}