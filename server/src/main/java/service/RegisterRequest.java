package service;

import java.util.Objects;

public class RegisterRequest {
    private final String username;
    private final String password;
    private final String email;

    RegisterRequest(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest request = (RegisterRequest) o;
        return Objects.equals(username, request.username) && Objects.equals(password, request.password) && Objects.equals(email, request.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }
}
