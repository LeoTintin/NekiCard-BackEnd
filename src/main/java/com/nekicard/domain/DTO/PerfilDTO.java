package com.nekicard.domain.DTO;

import java.time.LocalDate;

public record PerfilDTO(Long id, String email, String nome, String nomeSocial, LocalDate dataNascimento, String foto,
		String telefone, String redeSocial) {

}
