package com.nekicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekicard.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);;
}
