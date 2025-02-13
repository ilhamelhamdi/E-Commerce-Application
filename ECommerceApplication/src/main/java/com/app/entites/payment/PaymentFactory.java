package com.app.entites.payment;

import com.app.entites.Address;
import com.app.payloads.PaymentRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Payment createPayment(String paymentMethod, PaymentRequest paymentRequest){
        switch (paymentMethod.toUpperCase()){
            case "COD" -> {
                CODPayment payment = new CODPayment();
                Address address = modelMapper.map(paymentRequest.getAddress(), Address.class);
                payment.setAddress(address);
                return payment;
            }
            default -> {
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
            }
        }
    }
}
