package com.app.payloads;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private AddressDTO address;
}
