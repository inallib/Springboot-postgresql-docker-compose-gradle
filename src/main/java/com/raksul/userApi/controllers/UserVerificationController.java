package com.raksul.userApi.controllers;

import com.raksul.userApi.dto.UserInputDto;
import com.raksul.userApi.services.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserVerificationController extends BaseController{
    @Autowired
    UserVerificationService userVerificationService;

    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity login(@Valid @RequestBody UserInputDto userInputDto) throws Exception {
        return userVerificationService.doLogin(userInputDto);
    }

    @GetMapping(value="/secret", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getSecretString(HttpServletRequest httpServletRequest) throws Exception {
        return userVerificationService.validateTokenAndGetSecret(httpServletRequest);
    }
}
