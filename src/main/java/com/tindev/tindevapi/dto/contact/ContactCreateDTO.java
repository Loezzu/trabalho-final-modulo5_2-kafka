package com.tindev.tindevapi.dto.contact;

import com.tindev.tindevapi.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactCreateDTO {

    private String nome;

    private String email;

    private String assunto;

    @Enumerated(EnumType.STRING)
    private Tipo tipoContato;

    private String mensagem;
}
