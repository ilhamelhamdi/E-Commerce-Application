package com.app.payloads.mapper;

import com.app.entites.payment.CODPayment;
import com.app.entites.payment.Payment;
import com.app.payloads.AddressDTO;
import com.app.payloads.PaymentResponse;
import org.modelmapper.ModelMapper;

public class PaymentMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static PaymentResponse toPaymentResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse paymentResponse = modelMapper.map(payment, PaymentResponse.class);

        if (payment instanceof CODPayment codPayment) {
            AddressDTO addressDTO = modelMapper.map(codPayment.getAddress(), AddressDTO.class);
            paymentResponse.setAddress(addressDTO);
        }

        return paymentResponse;
    }
}