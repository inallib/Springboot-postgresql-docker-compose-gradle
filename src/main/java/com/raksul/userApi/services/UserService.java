package com.raksul.userApi.services;

import com.raksul.userApi.constants.Constants;
import com.raksul.userApi.dao.UserDao;
import com.raksul.userApi.dto.ErrorDto;
import com.raksul.userApi.dto.UserInputDto;
import com.raksul.userApi.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserVerificationService userVerificationService;

    public ResponseEntity doSignUp(UserInputDto userInputDto) throws Exception {
        UserResponseDto userResponseDto = userDao.saveUser(userInputDto);
        if (userResponseDto != null)
            return new ResponseEntity(userResponseDto, HttpStatus.CREATED);
        return new ResponseEntity(null, HttpStatus.EXPECTATION_FAILED);
    }

    public ResponseEntity doUpdateUser(String id, UserInputDto userInputDto, HttpServletRequest httpServletRequest) throws Exception {
        if (userVerificationService.validateTokenAndGetDto(httpServletRequest) != null) {
            UserResponseDto userResponseDto = userDao.updateUser(id, userInputDto);
            if (userResponseDto != null)
                return new ResponseEntity(userResponseDto, HttpStatus.CREATED);
        }
        return new ResponseEntity(new ErrorDto(Constants.ACCESS_DENIED), HttpStatus.BAD_REQUEST);
    }
}
