package com.recipemanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Recipes {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Recipe name is required!")
    private String name;

    private String recipeType;

    @NotEmpty(message = "Ingredients are required!")
    private String ingredients;

    @NotEmpty(message = "Instructions are required!")
    private String instructions;

    private LocalDateTime recipeWasAdded;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
