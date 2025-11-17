package com.recipemanagement.controllers;

import com.recipemanagement.models.User;
import com.recipemanagement.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, @RequestParam("confirmPassword") String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            return "redirect:/signup?error-passwordDidNotMatch";
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            return "redirect:/signup?error=usernameNotAvailable";
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userRepository.save(user);

        return "redirectL/login";
    }

    @GetMapping("/signup")
    public String registerUser(Model model) {
        model.addAttribute("title", "register");
        return "User/signup";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "User/login";
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userRepository.deleteById(userId);
    }



}
