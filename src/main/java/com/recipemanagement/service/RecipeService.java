package com.recipemanagement.service;

import com.recipemanagement.models.Recipe;
import com.recipemanagement.models.data.RecipeRepository;
import com.recipemanagement.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public void createRecipe(Recipe recipe) {
        recipe.setRecipeWasAdded(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    public Recipe findRecipe(int recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    public String updateRecipe(int recipeId, User currentUser, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(recipeId).orElse(null);

        if (existingRecipe != null && existingRecipe.getUser().equals(currentUser)) {
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            recipeRepository.save(existingRecipe);

            return "Recipe has been updated";
        } else {

            return "Unable to update recipe";
    }
    }

    public String removeRecipe(int recipeId, User user) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if(recipe != null && recipe.getUser().equals(user)) {
            recipeRepository.deleteById(recipeId);
            return "Recipe removed";
        } else if(recipe == null) {
            return "Recipe doesn't exist";
        } else {
            return "Unauthorized to delete recipe.";
        }
    }

    public boolean validateRecipe(Recipe recipe) {
        return (recipe != null && recipeRepository.existsById(recipe.getId()));
    }

    public Recipe getRecipeById(int recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    public Iterable<Recipe> getRecipes(){
        return recipeRepository.findAll();
    }
}
