package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class DeleteAuthTest {
    @Test
    @Order(1)
    @DisplayName("DeleteAUserTest")
    public void deleteAUserTest () throws Exception {
        try {
            String authToken = authorizationObject.createAuth("Lance");

            authorizationObject.deleteAuth(authToken);

            Assertions.assertNull(authorizationObject.getAuth(authToken).username(),
                    "Username should be null.");
            Assertions.assertNull(authorizationObject.getAuth(authToken).authToken(),
                    "AuthToken should be null");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in DeleteAUserTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("DeleteAUserFailTest")
    public void deleteAUserFailTest () throws Exception {
        try {
            String authToken = authorizationObject.createAuth("Lance");

            authorizationObject.deleteAuth(null);

            Assertions.assertNotNull(authorizationObject.getAuth(authToken).username(),
                    "Username should not be null.");
            Assertions.assertNotNull(authorizationObject.getAuth(authToken).authToken(),
                    "AuthToken should not be null");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in DeleteAUserTest");
            throw e;
        }
    }
}
