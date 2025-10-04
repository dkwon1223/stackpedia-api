package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.AuthProvider;
import com.dkwondev.stackpedia_v2_api.model.entity.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {

    Optional<UserAuthProvider> findByProviderAndProviderUserId(AuthProvider provider, String providerUserId);

    Optional<UserAuthProvider> findByUserUserIdAndProvider(Long userId, AuthProvider provider);

    boolean existsByUserUserIdAndProvider(Long userId, AuthProvider provider);
}