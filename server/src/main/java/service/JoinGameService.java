package service;

import requests.JoinGameRequest;
import results.JoinGameResult;
import chess.ChessGame;

import java.util.Objects;

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
                        request.getGameID() == null ||
                    (gameObject.getGame(request.getGameID()) == null)) {
                return new JoinGameResult("Error: bad request");
            } else if((request.getPlayerColor().equals(ChessGame.TeamColor.BLACK) &&
                    gameObject.getGame(request.getGameID()).blackUsername() != null &&
                    !Objects.equals(gameObject.getGame(request.getGameID()).blackUsername(), authorizationObject.getAuth(request.getAuthToken()).username())) ||
                    ((request.getPlayerColor().equals(ChessGame.TeamColor.WHITE) &&
                            gameObject.getGame(request.getGameID()).whiteUsername() != null) &&
                            !Objects.equals(gameObject.getGame(request.getGameID()).whiteUsername(), authorizationObject.getAuth(request.getAuthToken()).username()))) {
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

