package com.tindev.tindevapi.controller.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.service.LogService;
import com.tindev.tindevapi.service.ProdutorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@Api(value = "6 - Log API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"6 - Log API"})
public class LogController {

    private final LogService logService;

    @PostMapping("/registro")
    private void enviar(LogDTO logDTO) throws JsonProcessingException {
        logService.logPost(logDTO.getTipoLog(), logDTO.getDescricao());
    }

}