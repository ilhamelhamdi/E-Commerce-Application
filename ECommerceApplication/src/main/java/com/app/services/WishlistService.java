package com.app.services;

import com.app.payloads.WishlistDTO;
import java.util.List;

public interface WishlistService {

    // 1️⃣ Add a product to wishlist
    WishlistDTO addToWishlist(String userEmail, Long productId);

    // 2️⃣ Get all wishlist items for a user
    List<WishlistDTO> getUserWishlist(String userEmail);

    // 3️⃣ Remove a product from wishlist
    void removeFromWishlist(String userEmail, Long productId);
}
