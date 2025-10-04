package com.dkwondev.stackpedia_v2_api.model.entity;

import com.dkwondev.stackpedia_v2_api.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @NotEmpty(message = "Username cannot be empty.")
    @Column(name = "username", unique = true)
    private String username;

    @NonNull
    @NotEmpty(message = "Email cannot be empty.")
    @Column(name = "email", unique = true)
    @Email(message = "email must be valid")
    private String email;

    @ValidPassword
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserAuthProvider> authProviders = new HashSet<>();

    // Helper method to check if user has a specific auth provider
    public boolean hasAuthProvider(AuthProvider provider) {
        return authProviders.stream()
                .anyMatch(ap -> ap.getProvider() == provider);
    }

    // Helper method to check if user uses only OAuth (no password)
    public boolean isOAuthOnly() {
        return password == null || password.isEmpty();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled != null && enabled;
    }
}
