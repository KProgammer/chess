package service;

import java.util.Objects;

public class CreateGameResult {
    private final String pre;
    private final int gameID;
    private final String message;

    public CreateGameResult(String pre, Integer gameID, String message ){
        this.pre = pre;
        this.gameID = gameID;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateGameResult that = (CreateGameResult) o;
        return gameID == that.gameID && Objects.equals(pre, that.pre) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pre, gameID, message);
    }
}
