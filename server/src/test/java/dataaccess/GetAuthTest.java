package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;

public class GetAuthTest {
    @Test
    @Order(1)
    @DisplayName("GetAnAuthTest")
    public void getAnAuthTest () throws Exception {
        try {
            authorizationObject.clear();
            String authToken = authorizationObject.createAuth("Lance");

            Assertions.assertEquals(authorizationObject.getAuth(authToken).username(), "Lance",
                    "Username should be Lance.");
            Assertions.assertEquals(authorizationObject.getAuth(authToken).authToken(), authToken,
                    "AuthToken should be equal");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAnAuthTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("GetAnAuthTestFail")
    public void getAnAuthTestFail () throws Exception {
        try {
            Assertions.assertNull(authorizationObject.getAuth(null).username(),
                    "Username should be null.");
            Assertions.assertNull(authorizationObject.getAuth(null).authToken(),
                    "AuthToken should be null");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAnAuthTest");
            throw e;
        }
    }
}
