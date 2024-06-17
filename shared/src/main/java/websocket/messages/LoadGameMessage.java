package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private final Integer gameID;
    public final ChessGame game;
    private final Boolean blackHasWon;
    private final Boolean whiteHasWon;
    private final Boolean stalemate;
    private final ChessGame.TeamColor teamColor;

    public LoadGameMessage(Integer gameID, ChessGame game, Boolean blackHasWon, Boolean whiteHasWon, Boolean stalemate,
                           ChessGame.TeamColor teamColor){
        super(ServerMessageType.LOAD_GAME);
        this.gameID = gameID;
        this.game = game;
        this.blackHasWon = blackHasWon;
        this.whiteHasWon = whiteHasWon;
        this.stalemate = stalemate;
        this.teamColor = teamColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame getGame() {
        return game;
    }

    public Boolean getBlackHasWon() {
        return blackHasWon;
    }

    public Boolean getWhiteHasWon() {
        return whiteHasWon;
    }

    public Boolean getStalemate() {
        return stalemate;
    }

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }
}
