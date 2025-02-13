package com.app.entites.payment;

import com.app.entites.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("COD")
@AllArgsConstructor
@NoArgsConstructor
public class CODPayment extends Payment {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public String getPaymentMethod() {
        return "COD";
    }
}
