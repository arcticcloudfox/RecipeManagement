package com.recipemanagement.controllers;

import com.recipemanagement.models.Recipe;
import com.recipemanagement.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recipes")
public class RecipeManagementController {

    //Put in userService file. Incorporate user methods into controller.

    private final RecipeService recipeService;

    public RecipeManagementController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipes(@PathVariable int recipeId) {
        return recipeService.findRecipe(recipeId);
    }

    @GetMapping
    public Iterable<Recipe>getAllRecipes() {
        return recipeService.getRecipes();
    }

    @PutMapping("/{recipeId}")
    public String updateRecipe(@PathVariable int recipeId, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(recipeId, updatedRecipe);
    }





}
