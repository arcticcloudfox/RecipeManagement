package com.recipemanagement.models.data;

import com.recipemanagement.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
