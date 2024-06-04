package service;

import requests.JoinGameRequest;
import results.JoinGameResult;
import chess.ChessGame;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class JoinGameService {
    //JoinGame
    public JoinGameResult joinGame(JoinGameRequest request)  {
        try {
            if ((request.getAuthToken() == null) ||
                    (authorizationObject.getAuth(request.getAuthToken()).authToken() == null) ||
                    (authorizationObject.getAuth(request.getAuthToken()).username() == null)){
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
        } catch (Exception e) {
            System.out.println("Threw Runtime Error during joinGame");
        }

        return new JoinGameResult(null);
    }
}

