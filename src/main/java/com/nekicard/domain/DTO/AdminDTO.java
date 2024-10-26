package com.nekicard.domain.DTO;

import jakarta.validation.constraints.NotBlank;

public record AdminDTO(
	    Long id,
	    @NotBlank String email,
	    @NotBlank String nome,
	    @NotBlank String senha
	) {}
