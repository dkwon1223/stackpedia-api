package com.dkwondev.stackpedia_v2_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "stack")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    @NotEmpty(message = "Stack name cannot be empty")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NonNull
    @NotEmpty(message = "Stack must have a description")
    private String description;

    @Column(name = "slug", unique = true)
    @NonNull
    @NotEmpty(message = "Stack must have a URL-friendly slug.")
    @Size(min = 3, max = 30, message = "Slug must be between 3-30 characters.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens.")
    private String slug;
}
