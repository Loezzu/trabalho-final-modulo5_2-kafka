package com.tindev.tindevapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.dto.contact.ContactCreateDTO;
import com.tindev.tindevapi.enums.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ProdutorService produtorService;

    public void sendContact(String nome, String email, String assunto, Tipo tipoContato, String mensagem) throws JsonProcessingException {
        ContactCreateDTO contato = ContactCreateDTO.builder()
                .nome(nome)
                .email(email)
                .assunto(assunto)
                .tipoContato(tipoContato)
                .mensagem(mensagem)
                .build();
        produtorService.enviarContato(contato);

    }
}
