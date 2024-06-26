package websocket.commands;

public class ResignCommand extends UserGameCommand{
    private final Integer gameID;
    public static Boolean isForfeit;

    public ResignCommand(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
        this.isForfeit = isForfeit;
    }

    public Integer getGameID() {
        return gameID;
    }

    public static Boolean getIsForfeit() {
        return isForfeit;
    }
}
