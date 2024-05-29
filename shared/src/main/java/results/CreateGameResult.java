package results;

import java.util.Objects;

public class CreateGameResult {
    //private final String pre;
    private final Integer gameID;
    private final String message;

    public CreateGameResult(Integer gameID, String message ){
        this.gameID = gameID;
        this.message = message;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateGameResult that = (CreateGameResult) o;
        return Objects.equals(gameID, that.gameID) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, message);
    }
}
