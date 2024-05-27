package service;

import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class JoinGameService {
    //JoinGame
    public JoinGameResult JoinGame(JoinGameRequest request){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new JoinGameResult("Error: unauthorized");
        }

        if((request.getTeamColor().equals(ChessGame.TeamColor.BLACK) &&
                gameObject.getGame(request.getGameID()).blackUsername() != null) ||
                ((request.getTeamColor().equals(ChessGame.TeamColor.WHITE) &&
                        gameObject.getGame(request.getGameID()).whiteUsername() != null))){
            return new JoinGameResult("Error: already taken");
        }

        gameObject.updateGame(request.getGameID(), authorizationObject.getAuth(request.getAuthToken()).username(),
                request.getTeamColor());

        return new JoinGameResult(null);
    }
}

