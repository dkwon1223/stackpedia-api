package com.dkwondev.stackpedia_v2_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "username cannot be empty")
    @Column(name = "username")
    private String username;

    @NonNull
    @NotEmpty(message = "email cannot be empty")
    @Column(name = "email")
    private String email;

    @NonNull
    @NotEmpty(message = "password cannot be empty")
    @Column(name = "password")
    private String password;

}
