package com.dkwondev.stackpedia_v2_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "technology")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    @NotEmpty(message = "Technology name cannot be empty.")
    private String name;

    @Column(name = "shortDescription")
    @NonNull
    @NotEmpty(message = "Technology must have a brief summary.")
    @Size(max = 200, message = "Technology short description must be under 200 characters.")
    private String shortDescription;

    @Column(name = "description", columnDefinition = "TEXT")
    @NonNull
    @NotEmpty(message = "Technology must have a detailed description.")
    private String description;

    @Column(name = "slug", unique = true)
    @NonNull
    @NotEmpty(message = "Technology must have a URL-friendly slug.")
    @Size(min = 3, max = 30, message = "Slug must be between 3-30 characters.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens.")
    private String slug;

    @Column(name = "website_url")
    @URL(message = "Please provide a valid website URL.")
    private String websiteUrl;

    @Column(name = "github_url")
    @URL(message = "Please provide a valid GitHub URL.")
    private String githubUrl;

    @Column(name = "documentation_url")
    @URL(message = "Please provide a valid documentation URL.")
    private String documentationUrl;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "technology_category",
        joinColumns = @JoinColumn(name = "technology_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // Technology to Category helper methods
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getTechnologies().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getTechnologies().remove(this);
    }

}
