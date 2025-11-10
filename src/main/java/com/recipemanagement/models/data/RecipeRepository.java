package com.recipemanagement.models.data;

import com.recipemanagement.models.Recipe;
import com.recipemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAll();
    List<Recipe> findByUser(User user);
    Recipe findRecipeById(int id);
    void deleteById(int id);
}
