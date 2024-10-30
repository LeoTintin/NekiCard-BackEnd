package com.nekicard.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nekicard.domain.DTO.AdminDTO;
import com.nekicard.domain.DTO.AuthResponseDTO;
import com.nekicard.domain.model.Admin;
import com.nekicard.repository.AdminRepository;
import com.nekicard.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	TokenService tokenService;

	@Autowired
	AdminRepository userRepository;

	@Operation(description = "Metodo de login de Admin")
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AdminDTO data) {
		var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
		var auth = this.authManager.authenticate(userPassword);

		var token = tokenService.generateToken((Admin) auth.getPrincipal());

		return ResponseEntity.ok(new AuthResponseDTO(token));
	}

	@Operation(description = "Registrando um Admin")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid AdminDTO data) {
		if (this.userRepository.findByEmail(data.email()) != null)
			return ResponseEntity.badRequest().build();

		String ecryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		Admin newUser = new Admin(data.nome(), data.email(), ecryptedPassword);

		this.userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}
}
