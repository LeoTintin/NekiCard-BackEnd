package com.nekicard.domain.DTO;

import java.util.Date;

public record PerfilDTO(Long id, String email, String nome, String nomeSocial, Date dataNascimento, String foto,
		String telefone, String redeSocial) {

}
