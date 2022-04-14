package com.tindev.tindevapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.enums.TipoLog;
import com.tindev.tindevapi.service.ProdutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class ControllerTesteKafka {

    private final ProdutorService produtorService;

    @PostMapping("/registro")
    private void enviar(LogDTO logDTO) throws JsonProcessingException {
        produtorService.enviarLog(logDTO);
    }

}