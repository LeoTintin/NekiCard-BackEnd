package com.nekicard.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PerfilRequest {
    private String nome;
    private String email;
    private String nomeSocial;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dataNascimento;
    private String telefone;
    private String redeSocial;
    private MultipartFile arquivo;

}
