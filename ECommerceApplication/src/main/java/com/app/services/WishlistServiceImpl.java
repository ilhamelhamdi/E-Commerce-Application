package com.app.services;

import com.app.entites.User;
import com.app.entites.Cart;
import com.app.entites.CartItem;
import com.app.entites.Product;
import com.app.entites.Wishlist;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.WishlistDTO;
import com.app.repositories.UserRepo;
import com.app.repositories.ProductRepo;
import com.app.repositories.WishlistRepo;
import com.app.repositories.CartRepo;
import com.app.repositories.CartItemRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

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
        private CartRepo cartRepo;

        @Autowired
        private CartItemRepo cartItemRepo;

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

                // Check if the product exists in the wishlist before deleting
                Optional<Wishlist> wishlistItem = wishlistRepo.findByUserAndProduct(user, product);

                if (wishlistItem.isEmpty()) {
                        throw new APIException("Product is not in the wishlist!");
                }

                // Remove from wishlist
                wishlistRepo.deleteByUserAndProduct(user, product);
        }

        @Override
        public void moveToCart(String email, Long productId) {
                // Find the user
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

                // Find the product
                Product product = productRepo.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

                // Check if the product is in the wishlist
                Wishlist wishlistItem = wishlistRepo.findByUserAndProduct(user, product)
                                .orElseThrow(() -> new APIException("Product is not in the wishlist!"));

                // Find or create the user's cart
                Cart cart = cartRepo.findCartByEmailAndCartId(email, user.getCart().getCartId());
                if (cart == null) {
                        cart = new Cart();
                        cart.setUser(user);
                        cart.setTotalPrice(0.0);
                        cart = cartRepo.save(cart);
                }

                // Add the product to the cart
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1); // Default to 1 quantity
                cartItem.setProductPrice(product.getPrice());
                cartItem.setDiscount(product.getDiscount());

                cartItemRepo.save(cartItem);

                // Remove the product from the wishlist
                wishlistRepo.delete(wishlistItem);
        }

}
