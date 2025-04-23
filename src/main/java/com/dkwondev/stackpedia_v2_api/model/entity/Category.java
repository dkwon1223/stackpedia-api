package com.dkwondev.stackpedia_v2_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    @NotEmpty(message = "Category must have a name.")
    private String name;

    @Column(name = "description")
    @NonNull
    @NotEmpty(message = "Category must have a brief description")
    @Size(max = 500, message = "Category description must be 1-500 characters.")
    private String description;

    @Column(name = "slug")
    @NonNull
    @NotEmpty(message = "Category must have a URL friendly slug.")
    @Size(min = 3, max = 30, message = "Slug must be between 3-30 characters.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens.")
    private String slug;

    @ManyToMany
    private Set<Technology> technologies = new HashSet<>();

}
