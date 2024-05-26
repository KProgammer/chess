package service;

public class LoginResult {
    private final String username;
    private final String password;
    private final String message;

    public LoginResult (String username, String password, String message){

        this.username = username;
        this.password = password;
        this.message = message;
    }
}
