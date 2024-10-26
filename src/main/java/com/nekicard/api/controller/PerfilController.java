package com.nekicard.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.nekicard.domain.model.Perfil;
import com.nekicard.domain.model.PerfilRequest;
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
		return ResponseEntity.ok(findPerfil);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletePerfil(@PathVariable Long id) {
		perfilRepository.deleteById(id);
		perfilRepository.flush();
		return ResponseEntity.noContent().build();
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<Perfil> addPerfil(@ModelAttribute @Valid PerfilRequest perfilRequest) {
		Perfil newPerfil = new Perfil();
		newPerfil.setNome(perfilRequest.getNome());
		newPerfil.setEmail(perfilRequest.getEmail());
		newPerfil.setNomeSocial(perfilRequest.getNomeSocial());
		newPerfil.setDataNascimento(perfilRequest.getDataNascimento());
		newPerfil.setTelefone(perfilRequest.getTelefone());
		newPerfil.setRedeSocial(perfilRequest.getRedeSocial());
		perfilRepository.save(newPerfil);

		try {
			MultipartFile arquivo = perfilRequest.getArquivo();
			if (arquivo != null && !arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				String caminhoImagens = "C:\\Users\\Leo\\Downloads\\profilePictures\\";
				Path caminho = Paths.get(caminhoImagens + newPerfil.getId() + "_" + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				newPerfil.setFoto(newPerfil.getId() + "_" + arquivo.getOriginalFilename());
				perfilRepository.save(newPerfil);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(newPerfil);
	}

	@PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id, @ModelAttribute @Valid PerfilRequest perfilRequest) {
	    Optional<Perfil> optionalPerfil = perfilRepository.findById(id);

	    if (optionalPerfil.isPresent()) {
	        Perfil perfil = optionalPerfil.get();
	        perfil.setNome(perfilRequest.getNome());
	        perfil.setEmail(perfilRequest.getEmail());
	        perfil.setNomeSocial(perfilRequest.getNomeSocial());
	        perfil.setDataNascimento(perfilRequest.getDataNascimento());
	        perfil.setTelefone(perfilRequest.getTelefone());
	        perfil.setRedeSocial(perfilRequest.getRedeSocial());

	        // Salvar a foto se houver um novo arquivo
	        try {
	            MultipartFile arquivo = perfilRequest.getArquivo();
	            if (arquivo != null && !arquivo.isEmpty()) {
	                byte[] bytes = arquivo.getBytes();
	                String caminhoImagens = "C:\\Users\\Leo\\Downloads\\profilePictures\\";
	                Path caminho = Paths.get(caminhoImagens + perfil.getId() + "_" + arquivo.getOriginalFilename());
	                Files.write(caminho, bytes);

	                perfil.setFoto(perfil.getId() + "_" + arquivo.getOriginalFilename());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Opcional: tratar o erro de forma adequada, talvez lançando uma exceção customizada
	        }

	        // Salvar as alterações no perfil
	        perfilRepository.save(perfil);
	        return ResponseEntity.ok(perfil);
	    } else {
	        throw new EntityNotFoundException();
	    }
	}

}
