package com.app.services;

import com.app.payloads.WishlistDTO;
import java.util.List;

public interface WishlistService {

    WishlistDTO addToWishlist(String userEmail, Long productId);

    List<WishlistDTO> getUserWishlist(String userEmail);

    void removeFromWishlist(String userEmail, Long productId);

    void moveToCart(String email, Long productId);
}
