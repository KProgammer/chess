package websocket.commands;

public class LeaveCommand extends UserGameCommand {
    private final Integer gameID;

    public LeaveCommand(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }

    public Integer getGameID() {
        return gameID;
    }
}