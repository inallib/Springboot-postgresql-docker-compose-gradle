package com.raksul.userApi.services;

import com.raksul.userApi.constants.Constants;
import com.raksul.userApi.dao.UserVerificationDao;
import com.raksul.userApi.dto.ErrorDto;
import com.raksul.userApi.dto.SecretDto;
import com.raksul.userApi.dto.UserInputDto;
import com.raksul.userApi.dto.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class UserVerificationService {

    @Autowired
    UserVerificationDao userVerificationDao;

    public ResponseEntity doLogin(UserInputDto userInputDto) throws Exception {
        UserToken token = getToken(userInputDto);
        if (userVerificationDao.findAndUpdateUserToken(userInputDto, token.getToken()))
            return new ResponseEntity(token, HttpStatus.OK);
        return new ResponseEntity(null, HttpStatus.EXPECTATION_FAILED);
    }

    public ResponseEntity validateTokenAndGetSecret(HttpServletRequest httpServletRequest) throws Exception {
        SecretDto secretDto = validateTokenAndGetDto(httpServletRequest);
        if (secretDto != null)
            return new ResponseEntity(secretDto, HttpStatus.OK);
        return new ResponseEntity(new ErrorDto(Constants.TOKEN_INVALID), HttpStatus.BAD_REQUEST);
    }

    public SecretDto validateTokenAndGetDto(HttpServletRequest httpServletRequest) throws Exception {
        String bearerToken = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        if (isValidBearerToken(bearerToken))
            return userVerificationDao.fetchSecretIfValidToken(bearerToken.replace(Constants.BEARER, ""));
        return null;
    }

    private UserToken getToken(UserInputDto userInputDto) throws Exception {
        String input = userInputDto.getEmail() + java.util.UUID.randomUUID();
        MessageDigest md = MessageDigest.getInstance(Constants.MD5);
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return new UserToken(hashtext);
    }

    private boolean isValidBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith(Constants.BEARER) || !(authorization.length() == 39))
            return false;
        return true;
    }
}
