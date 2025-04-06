package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.MyUserDetails;
import com.Vidstream.vidstream.model.Users;
import com.Vidstream.vidstream.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Users user = userRepository.isExistsByUsername(username);
            if (user == null) {

                throw new CustomException("User not found", HttpStatus.NOT_FOUND);
            }

            return new MyUserDetails(user);

        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
