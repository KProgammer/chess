package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.*;

public class ClearTest {

    @Test
    @DisplayName("ClearAllGamesTest")
    public void clearAllGamesTest() {
        try {
            String authToken = authorizationObject.createAuth("practice");
            gameObject.createGame(1234, "practiceGame");
            userObject.createUser("Lance", "password", "LanceHasMail");

            new ClearService().clear();

            Assertions.assertEquals(authorizationObject.getAuth(authToken).username(), null,
                    "Didn't delete authorizations");
            Assertions.assertEquals(authorizationObject.getAuth(authToken).authToken(), null,
                    "Didn't delete authorizations");

            Assertions.assertEquals(gameObject.getGame(1234).gameID(), 0,
                    "Game still exists");
            Assertions.assertEquals(gameObject.getGame(1234).gameName(), null,
                    "Game still exists");
            Assertions.assertEquals(gameObject.getGame(1234).game(), null,
                    "Game still exists");
            Assertions.assertEquals(gameObject.getGame(1234).whiteUsername(), null,
                    "Game still exists");
            Assertions.assertEquals(gameObject.getGame(1234).blackUsername(), null,
                    "Game still exists");

            Assertions.assertEquals(userObject.getUser("Lance").username(), null, "User still exists");
            Assertions.assertEquals(userObject.getUser("Lance").password(), null, "User still exists");
            Assertions.assertEquals(userObject.getUser("Lance").email(), null, "User still exists");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in clearAllGamesTest");
        }
    }
}
