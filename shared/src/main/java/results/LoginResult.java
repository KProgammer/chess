package results;

import java.util.Objects;

public class LoginResult {
    private final String username;
    private final String authToken;
    private final String message;

    public LoginResult (String username, String authToken, String message){

        this.username = username;
        this.authToken = authToken;
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return Objects.equals(username, that.username) && Objects.equals(authToken, that.authToken) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authToken, message);
    }
}
