package com.raksul.userApi.controllers;

import com.raksul.userApi.dto.UserInputDto;
import com.raksul.userApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Validated
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity signUp(@Valid @RequestBody UserInputDto userInputDto) throws Exception {
        return userService.doSignUp(userInputDto);
    }

    @PatchMapping(value="/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateUser(@PathVariable(value = "id")String id, @Valid @RequestBody UserInputDto userInputDto, HttpServletRequest httpServletRequest) throws Exception {
        return userService.doUpdateUser(id, userInputDto, httpServletRequest);
    }
}
