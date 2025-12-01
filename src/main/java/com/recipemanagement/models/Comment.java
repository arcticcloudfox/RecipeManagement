package com.recipemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String text;

    @Column(name = "commenter_user_id")
    private int commenterUserId;

    private LocalDateTime commentCreationTime;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private User user;
}
