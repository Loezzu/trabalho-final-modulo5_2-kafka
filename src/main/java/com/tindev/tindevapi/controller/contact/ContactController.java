package com.tindev.tindevapi.controller.contact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tindev.tindevapi.enums.Tipo;
import com.tindev.tindevapi.service.ContactService;
import com.tindev.tindevapi.service.ProdutorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contato")
@RequiredArgsConstructor
@Api(value = "7 - Contact API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"7 - Contact API"})
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact-us")
    public void contactUs(@RequestParam String name, @RequestParam String email, @RequestParam String assunto, @RequestParam Tipo tipo, @RequestBody String message) throws JsonProcessingException {
        contactService.sendContact(name, email, assunto, tipo, message);
    }

}
