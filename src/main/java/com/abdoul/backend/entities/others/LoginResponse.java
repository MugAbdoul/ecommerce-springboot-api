package com.abdoul.backend.entities.others;

import com.abdoul.backend.entities.User;

public class LoginResponse {
    private AuthenticationResponse authenticationResponse;
    private User user;

    public LoginResponse(AuthenticationResponse authenticationResponse, User user) {
        this.authenticationResponse = authenticationResponse;
        this.user = user;
    }
    

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    public void setAuthenticationResponse(AuthenticationResponse authenticationResponse) {
        this.authenticationResponse = authenticationResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
