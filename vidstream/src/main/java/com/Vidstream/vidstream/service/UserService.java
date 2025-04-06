package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.dto.UserDTO;
import com.Vidstream.vidstream.model.Users;
import com.Vidstream.vidstream.repository.UserRepository;
import com.Vidstream.vidstream.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Converter converter;
    public UserDTO register(UserDTO userDTO){
        Users model = converter.DTOtoUser(userDTO);
    try{
            Long generatedId = userRepository.createNewUser(model);
            userDTO.setId(generatedId);
            return userDTO;
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
