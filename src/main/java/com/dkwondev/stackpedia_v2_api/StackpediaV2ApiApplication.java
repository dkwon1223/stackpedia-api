package com.dkwondev.stackpedia_v2_api;

import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class StackpediaV2ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackpediaV2ApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			Role userRole = roleRepository.save(new Role("USER"));

			User adminUser = new User("admin", "admin@gmail.com", passwordEncoder.encode("adminpass"));
			Set<Role> adminRoles = new HashSet<>();
			adminRoles.add(adminRole);
			adminUser.setAuthorities(adminRoles);
			userRepository.save(adminUser);

			User user = new User("user", "user@gmail.com", passwordEncoder.encode("userpass"));
			Set<Role> userRoles = new HashSet<>();
			userRoles.add(userRole);
			user.setAuthorities(userRoles);
			userRepository.save(user);
		};
	}
}
