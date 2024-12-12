package com.dn.shop.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany
    private List<Product> basket;

    private List<Product> cart;

    // Getters and Setters (Lombok will generate these due to @Getter and @Setter)

    // Override toString for debugging purposes
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", basket=" + basket +
                ", cart=" + cart +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities for the user
        // This could be a list of roles or permissions
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // Example: Add roles to the authorities list
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Add user role
        // Add more roles as needed
        return grantedAuthorities; 
    }

    // Implement other methods from UserDetails
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Assuming email is the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic
    }

    /**
     * Sets the basket for the user.
     * 
     * @param basket A list of Product objects to be set in the user's basket.
     */
    public void setCart(List<Product> basket) {
        this.basket = basket; // Use 'basket' as the field in the User class
    }

    public List<Product> getCart() {
        return cart != null ? cart : new ArrayList<>();
    }
}
