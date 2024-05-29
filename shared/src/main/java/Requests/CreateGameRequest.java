package Requests;

import java.util.Objects;

public class CreateGameRequest {
    private String gameName;
    private String authToken;

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

    public void setGameName(String newGameName){
        gameName = newGameName;
    }

    public void setAuthToken(String newAuthToken){
        authToken = newAuthToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateGameRequest request = (CreateGameRequest) o;
        return Objects.equals(gameName, request.gameName) && Objects.equals(authToken, request.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, authToken);
    }
}
