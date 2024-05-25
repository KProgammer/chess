package service;

public class CreateGameRequest {
    private final String gameName;
    private final String authToken;

    public CreateGameRequest(String gameName, String authToken){
        this.gameName = gameName;
        this.authToken = authToken;
    }

    public String getGameName() {
        return gameName;
    }

    public String getAuthToken() {
        return authToken;
    }
}
