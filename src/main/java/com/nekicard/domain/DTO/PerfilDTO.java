package com.nekicard.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Perfil do usuário")
public record PerfilDTO(
    Long id,
    @NotBlank String email,
    @NotBlank String nome,
    String nomeSocial,
    @NotNull LocalDate dataNascimento,
    @NotNull String foto,
    String telefone,
    String redeSocial
) {}
