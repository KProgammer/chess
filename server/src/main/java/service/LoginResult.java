package service;

import java.util.Objects;

public class LoginResult {
    private final String username;
    private final String password;
    private final String message;

    public LoginResult (String username, String password, String message){

        this.username = username;
        this.password = password;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, message);
    }
}
