package com.nekicard.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nekicard.domain.DTO.PerfilRequest;
import com.nekicard.domain.model.Perfil;
import com.nekicard.repository.PerfilRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("perfil")
public class PerfilController {

	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping
	public ResponseEntity listPerfil() {
		var allPerfil = perfilRepository.findAll();
		return ResponseEntity.ok(allPerfil);
	}

	@GetMapping("/{id}")
	public ResponseEntity findPerfil(@PathVariable Long id) {
		var findPerfil = perfilRepository.findById(id);
		if (findPerfil.isEmpty()) {
			return ResponseEntity.badRequest().body("Não foi encontrado um perfil com este ID");
		}
		return ResponseEntity.ok(findPerfil);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletePerfil(@PathVariable Long id) {
		perfilRepository.deleteById(id);
		perfilRepository.flush();
		return ResponseEntity.noContent().build();
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<?> addPerfil(@ModelAttribute @Valid PerfilRequest perfilRequest) {

		MultipartFile foto = perfilRequest.getFoto();
		if (foto == null || foto.isEmpty()) {
			return ResponseEntity.badRequest().body("Foto não pode ser um campo vazio");
		}

		String nome = perfilRequest.getNome();
		if (nome == null || nome.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Nome não pode ser um campo vazio");
		}

		String email = perfilRequest.getEmail();
		if (email == null || email.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("E-mail não pode ser um campo vazio");
		}

		LocalDate dataNascimento = perfilRequest.getDataNascimento();
		if (dataNascimento == null) {
			return ResponseEntity.badRequest().body("Data de Nascimento não pode ser um campo vazio");
		}

		Perfil newPerfil = new Perfil();
		newPerfil.setNome(perfilRequest.getNome());
		newPerfil.setEmail(perfilRequest.getEmail());
		newPerfil.setNomeSocial(perfilRequest.getNomeSocial());
		newPerfil.setDataNascimento(perfilRequest.getDataNascimento());
		newPerfil.setTelefone(perfilRequest.getTelefone());
		newPerfil.setRedeSocial(perfilRequest.getRedeSocial());

		try {

			byte[] bytes = foto.getBytes();
			String caminhoImagens = "C:\\Users\\Leo\\Downloads\\profilePictures\\";
			Path caminho = Paths.get(caminhoImagens + newPerfil.getId() + "_" + foto.getOriginalFilename());
			Files.write(caminho, bytes);

			newPerfil.setFoto(newPerfil.getId() + "_" + foto.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		perfilRepository.save(newPerfil);
		return ResponseEntity.ok(newPerfil);
	}

	@PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity<?> updatePerfil(@PathVariable Long id, @ModelAttribute @Valid PerfilRequest perfilRequest) {
		Optional<Perfil> optionalPerfil = perfilRepository.findById(id);

		String nome = perfilRequest.getNome();
		if (nome == null || nome.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Nome não pode ser um campo vazio");
		}

		String email = perfilRequest.getEmail();
		if (email == null || email.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("E-mail não pode ser um campo vazio");
		}

		LocalDate dataNascimento = perfilRequest.getDataNascimento();
		if (dataNascimento == null) {
			return ResponseEntity.badRequest().body("Data de Nascimento não pode ser um campo vazio");
		}

		if (optionalPerfil.isPresent()) {
			Perfil perfil = optionalPerfil.get();
			perfil.setNome(perfilRequest.getNome());
			perfil.setEmail(perfilRequest.getEmail());
			perfil.setNomeSocial(perfilRequest.getNomeSocial());
			perfil.setDataNascimento(perfilRequest.getDataNascimento());
			perfil.setTelefone(perfilRequest.getTelefone());
			perfil.setRedeSocial(perfilRequest.getRedeSocial());

			try {
				MultipartFile foto = perfilRequest.getFoto();

				if (foto != null && !foto.isEmpty()) {

					byte[] bytes = foto.getBytes();
					String caminhoImagens = "C:\\Users\\Leo\\Downloads\\profilePictures\\";
					Path caminho = Paths.get(caminhoImagens + perfil.getId() + "_" + foto.getOriginalFilename());
					Files.write(caminho, bytes);

					perfil.setFoto(perfil.getId() + "_" + foto.getOriginalFilename());
				}
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}

			perfilRepository.save(perfil);
			return ResponseEntity.ok(perfil);
		} else {
			throw new EntityNotFoundException();
		}
	}

}
