package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage {
    private final Integer gameID;
    public static ChessGame game;
    private final Boolean blackHasWon;
    private final Boolean whiteHasWon;
    private final ChessGame.TeamColor teamColor;

    public LoadGameMessage(Integer gameID, ChessGame chessGame, Boolean blackHasWon, Boolean whiteHasWon, ChessGame.TeamColor teamColor){
        this.gameID = gameID;
        this.game = chessGame;
        this.blackHasWon = blackHasWon;
        this.whiteHasWon = whiteHasWon;
        this.teamColor = teamColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public static ChessGame getGame() {
        return game;
    }

    public Boolean getBlackHasWon() {
        return blackHasWon;
    }

    public Boolean getWhiteHasWon() {
        return whiteHasWon;
    }

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }
}
