package com.Vidstream.vidstream.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class UserDTO {
    private Long id;
    @NotBlank(message="Full name required")
    private String fullName;
    @NotBlank(message="Username required")
    private String username;
    @NotBlank(message="Email required")
    private String email;
    @NotBlank(message="Password required")
    private String password;


    public UserDTO() {
    }

    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserDTO(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDTO(Long id, String fullName, String username, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
