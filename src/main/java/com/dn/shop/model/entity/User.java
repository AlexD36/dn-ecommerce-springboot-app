package com.dn.shop.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * User entity that implements Spring Security's UserDetails interface.
 * Represents a user in the system with basic information and shopping basket functionality.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User extends BaseEntity implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    /**
     * User's shopping basket containing selected products
     * Initialized as empty ArrayList to prevent NPE
     */
    @OrderBy("id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_basket",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false)
    )
    @Builder.Default
    private List<Product> basket = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    // Getters and Setters (Lombok will generate these due to @Getter and @Setter)

    /**
     * Custom toString implementation that excludes sensitive information
     * @return String representation of User without password
     */
    @Override
    public String toString() {
        return String.format(
            "User{id=%d, firstName='%s', lastName='%s', email='%s', basketSize=%d}",
            id,
            firstName,
            lastName,
            email,
            basket != null ? basket.size() : 0
        );
    }

    /**
     * Returns the authorities granted to the user.
     * Currently, all users have the "USER" role.
     * @return a list containing the user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    /**
     * Returns the email address as the username for authentication
     * @return the user's email address
     */
    @Override
    public String getUsername() {
        return email != null ? email : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
