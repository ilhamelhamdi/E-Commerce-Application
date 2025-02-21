package com.app.repositories;

import com.app.entites.Wishlist;
import com.app.entites.User;
import com.app.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w WHERE w.user.email = ?1")
    List<Wishlist> findByUserEmail(String email);

    Optional<Wishlist> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}
