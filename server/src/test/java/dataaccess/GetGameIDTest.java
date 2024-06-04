package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class GetGameIDTest {
    @Test
    @Order(1)
    @DisplayName("GetAgameIDTest")
    public void getAgameIDTest () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(3333,"HappyFeet");

            Assertions.assertEquals(gameObject.getGameID("HappyFeet"),3333,
                    "gameID should be 3333");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAgameIDTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("GetAnAuthTestFail")
    public void getAgameIDFail () throws Exception {
        try {
            gameObject.getGameID(null);
            Assertions.fail();
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAgameIDFail");
            Assertions.assertEquals("Invalid input.",e.getMessage(),"Failed to throw an exception.");
        }
    }
}
