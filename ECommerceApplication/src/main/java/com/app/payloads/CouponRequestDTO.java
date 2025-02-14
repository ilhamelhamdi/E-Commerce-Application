package com.app.payloads;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Hanya muncul jika ada nilai
public class CouponRequestDTO {
    private Long couponId;
}