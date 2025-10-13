package com.recipemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

}
