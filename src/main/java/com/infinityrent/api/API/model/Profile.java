package com.infinityrent.api.API.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String biography;

    private String profilePicturePath;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;
}
