package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);
}
