package com.nekicard.api.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nekicard.domain.DTO.UserDTO;
import com.nekicard.domain.DTO.AuthResponseDTO;
import com.nekicard.domain.model.User;
import com.nekicard.repository.UserRepository;
import com.nekicard.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	TokenService tokenService;

	@Autowired
	UserRepository userRepository;

	@Operation(description = "Metodo de login de Admin")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario logado com sucesso!"),
			@ApiResponse(responseCode = "400", description = "Credenciais invalidas"),
			@ApiResponse(responseCode = "500", description = "Erro no servidor") })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserDTO data) {

		try {
			String email = data.email();
			if (email == null || email.trim().isEmpty()) {
				return ResponseEntity.badRequest().body("E-mail não pode ser um campo vazio");
			}

			if (!email.endsWith("@neki-it.com.br") && !email.endsWith("@neki.com.br")) {
				return ResponseEntity.badRequest().body("E-mail deve terminar com @neki-it.com.br ou @neki.com.br");
			}

			String senha = data.senha();
			if (senha == null || senha.trim().isEmpty()) {
				return ResponseEntity.badRequest().body("Senha não pode ser um campo vazio");
			}

			var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
			var auth = this.authManager.authenticate(userPassword);

			var token = tokenService.generateToken((User) auth.getPrincipal());
			return ResponseEntity.ok(new AuthResponseDTO(token));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais inválidas");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");

		}

	}

	@Operation(description = "Registrando um Admin")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario registrado com sucesso!"),
			@ApiResponse(responseCode = "400", description = "Credenciais invalidas"),
			@ApiResponse(responseCode = "500", description = "Erro no servidor") })
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserDTO data) {

		try {

			String nome = data.nome();
			if (nome == null || nome.trim().isEmpty()) {
				throw new BadRequestException("Nome não pode ser um campo vazio");
			}

			String email = data.email();
			if (email == null || email.trim().isEmpty()) {
				throw new BadRequestException("E-mail não pode ser um campo vazio");
			}

			if (!email.endsWith("@neki-it.com.br") && !email.endsWith("@neki.com.br")) {
				throw new BadRequestException("E-mail deve terminar com @neki-it.com.br ou @neki.com.br");
			}

			String senha = data.senha();
			if (senha == null || senha.trim().isEmpty()) {
				throw new BadRequestException("Senha não pode ser um campo vazio");

			}

			String ecryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
			User newUser = new User(data.nome(), data.email(), ecryptedPassword);

			this.userRepository.save(newUser);

			return ResponseEntity.ok("Usuario registrado com sucesso!");

		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais inválidas");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");

		}

	}
}
