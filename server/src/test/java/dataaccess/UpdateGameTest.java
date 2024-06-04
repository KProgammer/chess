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
    public void updateAGameTest () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Abigail","penguins","dragonsrock@aol.com");

            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888,"Abigail", ChessGame.TeamColor.WHITE);
            Assertions.assertEquals(gameObject.getGame(8888).whiteUsername() ,"Abigail", "Wrong username listed.");

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("UpdateAGameTest2")
    public void updateAGameTest2 () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Abigail","penguins","dragonsrock@aol.com");

            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            gameObject.updateGame(8888,"MoreGames");
            Assertions.assertEquals(gameObject.getGame(8888).gameName(),"MoreGames", "Wrong gameName listed.");

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameTest2");
            throw e;
        }
    }

    @Test
    @Order(3)
    @DisplayName("UpdateAGameTest3")
    public void updateAGameTest3 () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Abigail","penguins","dragonsrock@aol.com");

            gameObject.clear();
            gameObject.createGame(8888,"RandomGame");

            ChessGame testgame = new ChessGame();
            gameObject.updateGame(8888,testgame);
            Assertions.assertEquals(gameObject.getGame(8888).game(),testgame, "Wrong ChessGame listed.");

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in UpdateAGameTest3");
            throw e;
        }
    }

    @Test
    @Order(4)
    @DisplayName("UpdateAGameFail")
    public void updateAGameFail () throws Exception {
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
    @Order(5)
    @DisplayName("UpdateAGameFail2")
    public void updateAGameFail2 () throws Exception {
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
    @Order(6)
    @DisplayName("UpdateAGameFail3")
    public void updateAGameFail3 () throws Exception {
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
