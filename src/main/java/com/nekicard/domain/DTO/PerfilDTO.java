package com.nekicard.domain.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerfilDTO(Long id, @NotBlank String email, @NotBlank String nome, String nomeSocial, @NotNull LocalDate dataNascimento, @NotNull String foto,
		String telefone, String redeSocial) {

}
