package com.recipemanagement.models.data;

import com.recipemanagement.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
}
