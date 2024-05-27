package service;

import java.util.Objects;

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
