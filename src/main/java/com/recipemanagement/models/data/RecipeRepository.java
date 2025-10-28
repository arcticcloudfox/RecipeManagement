package com.recipemanagement.models.data;

import com.recipemanagement.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAll();
    List<Recipe> findByUser();
    Recipe findRecipeById(int id);
    void deleteById(int id);
}
