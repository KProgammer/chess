package websocket.commands;

public class ResignCommand extends UserGameCommand{
    public static Boolean isForfeit;

    public ResignCommand(String authToken, Boolean isForfeit) {
        super(authToken);
        this.isForfeit = isForfeit;
    }
}
