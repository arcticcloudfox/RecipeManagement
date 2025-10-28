package com.recipemanagement.controllers;

import com.recipemanagement.models.Recipe;
import com.recipemanagement.models.User;
import com.recipemanagement.models.data.RecipeRepository;
import com.recipemanagement.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recipe")
public class RecipeManagementController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    //retrieves recipes by id
    @GetMapping("/{id}")
    public String getRecipes(@PathVariable int id, Model model) {
        Recipe recipe = (Recipe) recipeRepository.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "view";
    }

    //shows the add form to add a recipe
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "add";
    }

    //gets task by specific user (will need to finish once authentication it implemented
    @GetMapping("")
    public String getAllRecipesByUser(Model model) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<Recipe> recipes = recipeRepository.findByUser(user);
        model.addAttribute("recipes", recipes);
        return "index";
    }



}
