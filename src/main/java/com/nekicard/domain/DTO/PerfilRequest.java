package com.nekicard.domain.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PerfilRequest {

	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	private String nomeSocial;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull
	private LocalDate dataNascimento;
	private String telefone;
	private String redeSocial;
	@NotNull
	private MultipartFile foto;

}
