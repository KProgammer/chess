package service;

public class CreateGameResult {
    private final String pre;
    private final int gameID;
    private final String message;

    public CreateGameResult(String pre, Integer gameID, String message ){
        this.pre = pre;
        this.gameID = gameID;
        this.message = message;
    }
}
