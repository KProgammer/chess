package service;

import chess.ChessGame;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class JoinGameService {
    //JoinGame
    public JoinGameResult JoinGame(JoinGameRequest request){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new JoinGameResult("Error: unauthorized");
        } else if (request.getPlayerColor() == null ||
                    request.getGameID() == null) {
            return new JoinGameResult("Error: bad request");
        } else if((request.getPlayerColor().equals(ChessGame.TeamColor.BLACK) &&
                gameObject.getGame(request.getGameID()).blackUsername() != null) ||
                ((request.getPlayerColor().equals(ChessGame.TeamColor.WHITE) &&
                        gameObject.getGame(request.getGameID()).whiteUsername() != null))) {
            return new JoinGameResult("Error: already taken");
        }

        gameObject.updateGame(request.getGameID(), authorizationObject.getAuth(request.getAuthToken()).username(),
                request.getPlayerColor());

        return new JoinGameResult(null);
    }
}

