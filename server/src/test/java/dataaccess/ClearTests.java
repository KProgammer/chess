package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.*;
import static server.Server.userObject;

public class ClearTests {
    @Test
    @Order(1)
    @DisplayName("AuthorizationClearTest")
    public void authorizationClearTest () throws Exception {
        try {
            String authToken = authorizationObject.createAuth("Gavin");

            authorizationObject.clear();

            Assertions.assertEquals(authorizationObject.getAuth(authToken).authToken(),null, "User Gavin was not cleared.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("GameClearTest")
    public void gameClearTest () throws Exception {
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

    @Test
    @Order(3)
    @DisplayName("UserClearsTest")
    public void userClearsTest () throws Exception {
        try {
            userObject.createUser("Gavin","CowsAreAwesome","IloveCows@aol.com");
            userObject.createUser("Lance","NorwayIsAwesome","IloveNorway@aol.com");
            userObject.createUser("Kendall","RocketsAreAwesome","IloveRockets@aol.com");

            userObject.clear();

            Assertions.assertEquals(userObject.getUser("Gavin").username(),null, "User Gavin was not cleared.");
            Assertions.assertEquals(userObject.getUser("Lance").password(),null, "User Lance was not cleared.");
            Assertions.assertEquals(userObject.getUser("Kendall").email(),null, "User Kendall was not cleared.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }
}
