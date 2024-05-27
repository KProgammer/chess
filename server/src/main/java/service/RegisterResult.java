package service;

public class RegisterResult {
    private final String username;
    private final String authToken;
    private final String message;

    public RegisterResult(String username, String authToken, String message){

        this.username = username;
        this.authToken = authToken;
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }
}
