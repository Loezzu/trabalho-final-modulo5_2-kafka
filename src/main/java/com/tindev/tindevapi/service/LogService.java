package com.tindev.tindevapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.enums.TipoLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class LogService {

    private ProdutorService produtorService;

    public void logPost(TipoLog tipoLog, String mensagem) throws JsonProcessingException {
        LogDTO log = LogDTO.builder()
                .tipoLog(tipoLog)
                .descricao(mensagem).build();
        produtorService.enviarLog(log);
    }
}
