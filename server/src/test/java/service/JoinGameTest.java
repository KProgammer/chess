package service;

import chess.ChessGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class JoinGameTest {
    @Test
    @DisplayName("JoinGameTest")
    public void JoinGameTest() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",authToken));

        int gameID = gameObject.getGameID("practiceGame");
        new JoinGameService().JoinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK,gameID));

        Assertions.assertEquals(gameObject.getGame(gameID).blackUsername(), "practice",
                "practice didn't join the game as the black team");

        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame2",authToken));
        gameID = gameObject.getGameID("practiceGame2");
        new JoinGameService().JoinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE,gameID));

        Assertions.assertEquals(gameObject.getGame(gameID).whiteUsername(), "practice",
                "practice didn't join the game as the white team");

        Assertions.assertEquals(new JoinGameService().JoinGame(new JoinGameRequest(null, ChessGame.TeamColor.BLACK, gameID)),
                new JoinGameResult("Error: unauthorized"),"Unauthorized Error wasn't thrown");

        Assertions.assertEquals(new JoinGameService().JoinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID)),
                new JoinGameResult("Error: already taken"),"Already taken error wasn't thrown");
    }
}
