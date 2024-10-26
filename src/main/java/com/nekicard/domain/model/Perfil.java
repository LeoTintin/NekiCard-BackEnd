package com.nekicard.domain.model;

import java.util.Date;

import com.nekicard.domain.DTO.PerfilDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String nome;
	private String nomeSocial;
	private Date dataNascimento;
	private String foto;
	private String telefone;
	private String redeSocial;

	public Perfil (PerfilDTO perfilDTO) {
		this.nome = perfilDTO.nome();
		this.email = perfilDTO.email();
		this.nomeSocial = perfilDTO.nomeSocial();
		this.dataNascimento = perfilDTO.dataNascimento();
		this.foto = perfilDTO.foto();
		this.telefone = perfilDTO.telefone();
		this.redeSocial = perfilDTO.redeSocial();
	}
}