package dataaccess;

import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.gameObject;
import static server.Server.userObject;

public class UpdateGameTest {
    @Test
    @Order(1)
    @DisplayName("UpdateAGameTest")
    public void UpdateAGameTest () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Abigail","penguins","dragonsrock@aol.com");

            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888,"Abigail", ChessGame.TeamColor.WHITE);
            Assertions.assertEquals(gameObject.getGame(8888).whiteUsername() ,"Abigail", "Wrong username listed.");

            gameObject.updateGame(8888,"MoreGames");
            Assertions.assertEquals(gameObject.getGame(8888).gameName(),"MoreGames", "Wrong gameName listed.");

            ChessGame Testgame = new ChessGame();
            gameObject.updateGame(8888,Testgame);
            Assertions.assertEquals(gameObject.getGame(8888).game(),Testgame, "Wrong ChessGame listed.");

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("UpdateAGameFail")
    public void UpdateAGameFail () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888,null, ChessGame.TeamColor.WHITE);

            Assertions.fail();
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameFail");
            Assertions.assertEquals("Invalid input.",e.getMessage(),"Didn't match expected error message.");
        }
    }

    @Test
    @Order(3)
    @DisplayName("UpdateAGameFail2")
    public void UpdateAGameFail2 () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888, (String) null);
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameFail2");
            Assertions.assertEquals("Invalid input.",e.getMessage(),"Didn't match expected error message.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("UpdateAGameFail3")
    public void UpdateAGameFail3 () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888,(ChessGame) null);

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameFail3");
            Assertions.assertEquals("Invalid input.",e.getMessage(),"Didn't match expected error message.");
        }
    }
}
