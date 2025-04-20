package com.dkwondev.stackpedia_v2_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "technology")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    @NotEmpty(message = "Technology name cannot be empty")
    private String name;

    @Column(name = "shortDescription")
    @NonNull
    @NotEmpty(message = "Technology must have a brief summary")
    private String shortDescription;

    @Column(name = "description")
    @NonNull
    @NotEmpty(message = "Technology must have a detailed description")
    private String description;

    @Column(name = "slug")
    @NonNull
    @NotEmpty(message = "Technology must have a URL-friendly slug")
    private String slug;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "documentation_url")
    private String documentationUrl;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
