package com.recipemanagement.service;

import com.recipemanagement.models.User;
import com.recipemanagement.models.data.UserRepository;
import com.recipemanagement.security.RecipeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RecipeUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("Couldn't find user");
        }

        return new RecipeUserDetails(user);
    }

}
