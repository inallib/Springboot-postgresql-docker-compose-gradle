package com.raksul.userApi.dao;

import com.raksul.userApi.dto.SecretDto;
import com.raksul.userApi.dto.UserInputDto;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserVerificationDao extends BaseDao {

    public boolean findAndUpdateUserToken(UserInputDto userInputDto, String token) {
        String query = "UPDATE users SET token='" + token+ "', tokenvalid=true WHERE email='" + userInputDto.getEmail() + "' AND password='" + userInputDto.getPassword() + "'";
        return update(query);
    }

    public SecretDto fetchSecretIfValidToken(String token) throws Exception {
        String query = "SELECT ID FROM users WHERE token='" + token + "' AND tokenvalid=true";
        return mapResulSetToResponse(fetch(query));
    }

    private SecretDto mapResulSetToResponse(ResultSet resultSet) throws SQLException {
        if (resultSet != null){
            SecretDto secretDto = null;
            while ( resultSet.next() ) {
                secretDto = new SecretDto();
                secretDto.setUser_id(resultSet.getInt("id"));
            }
            closeConnection();
            return secretDto;
        }
        return null;
    }
}
