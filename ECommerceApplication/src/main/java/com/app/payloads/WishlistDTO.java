package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {
    private Long wishlistId;
    private String userEmail;
    private Long productId;
    private String productName;
}
