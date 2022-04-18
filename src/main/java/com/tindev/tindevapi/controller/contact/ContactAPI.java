package com.tindev.tindevapi.controller.contact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.dto.address.AddressCreateDTO;
import com.tindev.tindevapi.dto.address.AddressDTO;
import com.tindev.tindevapi.enums.Tipo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api
@Validated
public interface ContactAPI {

    @ApiOperation(value = "Entre em contato conosco")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tudo certo!"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    void contactUs(@RequestParam String name, @RequestParam String email, @RequestParam String assunto, @RequestParam Tipo tipo, @RequestBody String message) throws JsonProcessingException;

}