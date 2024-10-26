package com.nekicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.nekicard.domain.model.Admin;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByEmail(String email);;
}
