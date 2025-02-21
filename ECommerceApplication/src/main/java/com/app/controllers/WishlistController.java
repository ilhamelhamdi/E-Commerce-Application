package com.app.controllers;

import com.app.payloads.APIResponse;
import com.app.payloads.WishlistDTO;
import com.app.services.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/public/users/{email}/wishlist/{productId}")
    public ResponseEntity<WishlistDTO> addToWishlist(
            @PathVariable String email,
            @PathVariable Long productId) {

        WishlistDTO wishlistDTO = wishlistService.addToWishlist(email, productId);
        return new ResponseEntity<>(wishlistDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/users/{email}/wishlist")
    public ResponseEntity<List<WishlistDTO>> getUserWishlist(@PathVariable String email) {

        List<WishlistDTO> wishlistItems = wishlistService.getUserWishlist(email);
        return new ResponseEntity<>(wishlistItems, HttpStatus.OK);
    }

    @DeleteMapping("/public/users/{email}/wishlist/{productId}")
    public ResponseEntity<APIResponse> removeFromWishlist(
            @PathVariable String email,
            @PathVariable Long productId) {

        wishlistService.removeFromWishlist(email, productId);

        return new ResponseEntity<APIResponse>(
                new APIResponse("Product removed from wishlist successfully!", true),
                HttpStatus.OK);
    }

}
