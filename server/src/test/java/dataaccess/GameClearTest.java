package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class GameClearTest {
    @Test
    @DisplayName("GameClearTest")
    public void GameClearTest () throws Exception {
        try {
            gameObject.createGame(2222,"SadGame");

            gameObject.clear();

            Assertions.assertNull(gameObject.getGame(2222).whiteUsername(), "whiteUsername was not cleared.");
            Assertions.assertNull(gameObject.getGame(2222).blackUsername(), "blackUsername was not cleared.");
            Assertions.assertNull(gameObject.getGame(2222).gameName(), "gameName was not cleared.");
            Assertions.assertNull(gameObject.getGame(2222).game(), "game was not cleared.");
            Assertions.assertEquals(gameObject.getGame(2222).gameID(),0, "gameID was not cleared.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in GameClearTests");
            throw e;
        }
    }
}
