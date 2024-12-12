package com.dn.shop.repository;

import com.dn.shop.model.entity.User;
import com.dn.shop.model.dto.cart.CartDTO;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User , Long> {

    @Query("Select u from User u where u.id = :id")
    Optional<User> getUserById(@Param("id") Long id);
    @Query("select count(u) > 0 from User u where u.email = :email")
    Boolean findUserByEmail(@Param("email") String email);

    @Query("SELECT count(u) > 0 from User u where u.email = :email OR u.firstName = :firstname AND u.lastName = :lastname")
    Boolean checkIfUserExists(@Param("firstname") String firstname , @Param("lastname") String lastname , @Param("email") String email);


    @Modifying
    @Transactional
    @Query("DELETE User u WHERE u.email = :email")
    void deleteUserByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :newEmail where u.email = :oldEmail")
    void updateUserEmail(@Param("oldEmail") String oldEmail , @Param("newEmail") String newEmail);

    Optional<User> findByEmail(String email);

    void saveCart(CartDTO cartDTO);

}
