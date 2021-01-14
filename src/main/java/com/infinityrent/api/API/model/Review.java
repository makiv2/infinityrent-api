package com.infinityrent.api.API.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id_recipient", nullable=false, referencedColumnName = "id")
    private User user_recipient;

    @ManyToOne
    @JoinColumn(name="user_id_sender", nullable=false, referencedColumnName = "id")
    private User user_sender;

    @NotBlank
    @Size(min=1, max=5)
    private int rating;

    private String message;
}
