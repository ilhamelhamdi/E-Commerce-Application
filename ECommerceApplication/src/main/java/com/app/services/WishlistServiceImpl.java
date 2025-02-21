package com.app.services;

import com.app.entites.User;
import com.app.entites.Product;
import com.app.entites.Wishlist;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.WishlistDTO;
import com.app.repositories.UserRepo;
import com.app.repositories.ProductRepo;
import com.app.repositories.WishlistRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class WishlistServiceImpl implements WishlistService {

        @Autowired
        private WishlistRepo wishlistRepo;

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private ProductRepo productRepo;

        @Autowired
        private ModelMapper modelMapper;

        @Override
        public WishlistDTO addToWishlist(String userEmail, Long productId) {
                // Find the user
                User user = userRepo.findByEmail(userEmail)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

                // Find the product
                Product product = productRepo.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

                // Check if product is already in wishlist
                if (wishlistRepo.findByUserAndProduct(user, product).isPresent()) {
                        throw new IllegalStateException("Product is already in the wishlist!");
                }

                // Create wishlist entry
                Wishlist wishlist = new Wishlist();
                wishlist.setUser(user);
                wishlist.setProduct(product);

                Wishlist savedWishlist = wishlistRepo.save(wishlist);

                return modelMapper.map(savedWishlist, WishlistDTO.class);
        }

        @Override
        public List<WishlistDTO> getUserWishlist(String email) {
                List<Wishlist> wishlists = wishlistRepo.findByUserEmail(email);

                return wishlists.stream()
                                .map(wishlist -> modelMapper.map(wishlist, WishlistDTO.class))
                                .collect(Collectors.toList());
        }

        @Override
        public void removeFromWishlist(String userEmail, Long productId) {
                // Find the user
                User user = userRepo.findByEmail(userEmail)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

                // Find the product
                Product product = productRepo.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

                // Remove from wishlist
                wishlistRepo.deleteByUserAndProduct(user, product);
        }
}
