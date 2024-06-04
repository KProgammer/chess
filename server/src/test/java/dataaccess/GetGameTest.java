package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.gameObject;

public class GetGameTest {
    @Test
    @Order(1)
    @DisplayName("GetAGameTest")
    public void GetAGameTest () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(4444,"UglyFeet");

            Assertions.assertEquals(gameObject.getGame(4444).gameID(),4444,
                    "gameID should be 4444");
            Assertions.assertEquals(gameObject.getGame(4444).gameName(),"UglyFeet",
                    "gameName should be UglyFeet");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAGameTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("GetAGameFail")
    public void GetAGameFail () throws Exception {
        try {
            gameObject.getGameID(null);
            Assertions.fail();
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAGameFail");
            Assertions.assertEquals("Invalid input.",e.getMessage(),"Failed to throw an exception.");
        }
    }
}
