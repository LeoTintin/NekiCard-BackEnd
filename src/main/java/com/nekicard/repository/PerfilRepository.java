package com.nekicard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nekicard.domain.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Optional<Perfil> findByEmail(String email);

}
