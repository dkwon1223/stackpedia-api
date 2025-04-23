package com.dkwondev.stackpedia_v2_api;

import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.repository.CategoryRepository;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.TechnologyRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class StackpediaV2ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackpediaV2ApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, TechnologyRepository technologyRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
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

			Technology react = new Technology("React", "A JavaScript library for building web applications", "React is a JavaScript library for building user interfaces, particularly single-page applications. Created by Facebook (now Meta), React has become one of the most popular front-end development tools since its public release in 2013.\n" +
					"At its core, React uses a component-based architecture where user interfaces are built from small, reusable pieces. This modular approach makes code more maintainable and easier to debug. React uses a virtual DOM (Document Object Model) implementation that optimizes rendering performance by minimizing actual DOM manipulations.\n" +
					"One of React's key features is JSX, a syntax extension that allows you to write HTML-like code within JavaScript. This creates a more intuitive development experience where structure, logic, and styling can exist in a single file or component.\n" +
					"React is often described as just the \"view\" layer in an application, focusing specifically on user interface rendering. It can be combined with other libraries or frameworks for state management (like Redux), routing (like React Router), and other functionalities to build complete applications.", "react");
			Technology springBoot = new Technology("Spring Boot", "A Java framework for building Java applications", "Spring Boot is a powerful framework for building Java applications, specifically designed to simplify the development of production-grade Spring applications. Created by Pivotal (now part of VMware), Spring Boot takes an opinionated view of the Spring platform to minimize the configuration required by developers.\n" +
					"Spring Boot eliminates much of the boilerplate code and configuration that was traditionally needed with Spring Framework. It accomplishes this through \"auto-configuration,\" which automatically sets up your application based on the dependencies you've added to your project.\n" +
					"The framework includes embedded servers like Tomcat or Jetty, allowing you to create standalone applications that can run without external web server installation. Its starter dependencies streamline your build configuration, bundling commonly used libraries together.\n" +
					"Spring Boot also provides production-ready features like health checks, metrics, and externalized configuration, making it easier to deploy your applications to various environments.\n" +
					"To get started with Spring Boot, developers typically use the Spring Initializer (start.spring.io) to generate a project structure with the necessary dependencies for their specific needs.", "spring-boot");
			technologyRepository.saveAll(List.of(react, springBoot));

			Category frontendLibrary = new Category("Frontend Library", "Frontend libraries are reusable code collections that simplify UI development by providing pre-built components and tools. They handle DOM manipulation, state management, and user interactions while offering consistent rendering across browsers. Modern frontend libraries employ component-based architecture and virtual DOM concepts to improve performance and developer experience.", "frontend-library");
			categoryRepository.save(frontendLibrary);
		};
	}
}
