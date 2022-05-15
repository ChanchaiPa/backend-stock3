package com.demo.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.stock.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
