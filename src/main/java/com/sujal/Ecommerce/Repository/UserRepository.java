package com.sujal.Ecommerce.Repository;

import com.sujal.Ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<List<User>> findByRoleIn(List<String> role);
}
