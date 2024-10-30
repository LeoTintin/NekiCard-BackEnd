package com.nekicard.domain.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PerfilRequest {

	@NotBlank(message = "Nome não pode ser vazio")
	@Schema(required = true, description = "Nome do perfil")
	private String nome;

	@NotBlank(message = "E-mail não pode ser vazio")
	@Schema(required = true, description = "Email do perfil")
	private String email;
	
	@Schema(description = "Nome social do perfil")
	private String nomeSocial;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull(message = "Data de nascimento não pode ser vazio")
	@Schema(required = true, description = "Data de nascimento do perfil")
	private LocalDate dataNascimento;
	
	@Schema(description = "Telefone do perfil")
	private String telefone;
	
	@Schema(description = "Rede social do perfil")
	private String redeSocial;
	
	@NotNull(message = "Foto não pode ser vazio")
	@Schema(required = true, description = "Foto do perfil")
	private MultipartFile foto;

}
