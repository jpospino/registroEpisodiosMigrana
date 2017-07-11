package com.sinergia.registroepisodiosdemigrana.Activities.Dto;

/**
 * Created by juan on 10/07/17.
 */

public class LoginResult {
    private String status;
    private String token;

    public LoginResult(String status, String token)
    {
        this.setStatus(status);
        this.setToken(token);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
