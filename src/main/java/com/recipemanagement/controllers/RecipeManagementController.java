package com.recipemanagement.controllers;

import com.recipemanagement.models.Recipe;
import com.recipemanagement.models.User;
import com.recipemanagement.models.data.RecipeRepository;
import com.recipemanagement.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    //gets task by specific user
    @GetMapping("")
    public String getAllRecipesByUser(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<Recipe> recipes = recipeRepository.findByUser(user);
        model.addAttribute("recipes", recipes);
        return "index";
    }

    //enables user to add a recipe
    @PostMapping("/add")
    public String addRecipe(@ModelAttribute Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUsername(currentPrincipalName).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + currentPrincipalName);
        }

        recipe.setUser(user);

        recipeRepository.save(recipe);

        return "redirect:/recipe";
    }

    //allows user to edit a recipe
    @PostMapping("/edit/{id}")
    public String editRecipe(@PathVariable int id, @ModelAttribute("recipe") Recipe recipeDetails) {
        Recipe recipe = (Recipe) recipeRepository.findRecipeById(id);
        recipe.setRecipeType(recipeDetails.getRecipeType());
        recipe.setName(recipeDetails.getName());
        recipe.setIngredients(recipeDetails.getIngredients());
        recipe.setInstructions(recipeDetails.getInstructions());
        recipeRepository.save(recipe);
        return "redirect:/recipe";
    }

    //allows user to delete recipes
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable int id) {
        recipeRepository.deleteById(id);
        return "redirect:/recipe";
    }

    //shows the edit form when a user edits a recipe
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Recipe recipe = (Recipe) recipeRepository.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "edit";
    }





}
