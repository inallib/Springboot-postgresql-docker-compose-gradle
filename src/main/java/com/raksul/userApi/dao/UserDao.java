package com.raksul.userApi.dao;

import com.raksul.userApi.dto.UserInputDto;
import com.raksul.userApi.dto.UserResponseDto;
import com.raksul.userApi.utils.Util;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao extends BaseDao {

    public UserResponseDto saveUser(UserInputDto userInputDto) throws Exception {
        String query = "INSERT INTO users (email, password, created_at) " +
                "VALUES('"+ userInputDto.getEmail() + "', '"+ userInputDto.getPassword() +"', '" + Util.getCurrentTimeStamp() +"')";
        if (update(query)){
            ResultSet resultSet = getResultSet(userInputDto.getEmail());;
            return mapResulSetToResponse(resultSet);
        }
        return null;
    }

    public UserResponseDto updateUser(String id, UserInputDto userInputDto) throws Exception {
        if (update(getQuery(id, userInputDto))){
            ResultSet resultSet = getResultSet(userInputDto.getEmail());
            return mapResulSetToResponse(resultSet);
        }
        return null;
    }

    private ResultSet getResultSet(String email) throws Exception {
        return fetch("SELECT * FROM users where email='" + email + "'");
    }

    private String getQuery(String id, UserInputDto userInputDto) {
        if (userInputDto.getEmail() == null && userInputDto.getPassword() != null) {
            return "UPDATE users SET password='" + userInputDto.getPassword() + "', updated_at='" + Util.getCurrentTimeStamp() + "' WHERE ID='" + id + "'";
        } else if (userInputDto.getEmail() != null && userInputDto.getPassword() == null) {
            return "UPDATE users SET email='" + userInputDto.getEmail() + "', updated_at='" + Util.getCurrentTimeStamp() + "' WHERE ID='" + id + "'";
        } else {
            return "UPDATE users SET email='" + userInputDto.getEmail() + "', password='" + userInputDto.getPassword() + "'" +
                    ", updated_at='" + Util.getCurrentTimeStamp() + "' WHERE ID='" + id + "'";
        }
    }

    private UserResponseDto mapResulSetToResponse(ResultSet resultSet) throws SQLException {
        if (resultSet != null){
            UserResponseDto userResponseDto = null;
            while ( resultSet.next() ) {
                userResponseDto = new UserResponseDto();
                userResponseDto.setId(resultSet.getInt("id"));
                userResponseDto.setCreatedAt(resultSet.getTimestamp("created_at"));
                userResponseDto.setEmail(resultSet.getString("email"));
                userResponseDto.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            }
            closeConnection();
            return userResponseDto;
        }
        return null;
    }
}
