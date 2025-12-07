package com.recipemanagement.controllers;

import com.recipemanagement.models.User;
import com.recipemanagement.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.recipemanagement.dto.SignUpDTO;
import com.recipemanagement.dto.SignInDTO;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signup") SignUpDTO dto) {
        if (!dto.password.equals(dto.confirmPassword)) {
            return "redirect:/signup?error-passwordDidNotMatch";
        }

        if (userRepository.existsByUsername(dto.username)) {
            return "redirect:/signup?error=usernameNotAvailable";
        }

        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(passwordEncoder.encode(dto.password));

        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String registerUser(Model model) {
        model.addAttribute("title", "register");
        return "User/signup";
    }

    public String login(@ModelAttribute("login") SignInDTO dto) {
        Optional<User> userOptional = userRepository.findByUsername(dto.username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(dto.password, user.getPassword())) {
                return "redirect:/home";

            }
        }
        return "redirect:/login?error=invalidCredentials";
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
