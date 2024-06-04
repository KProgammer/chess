package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;

public class AuthorizationClearTest {
    @Test
    @DisplayName("AuthorizationClearTest")
    public void AuthorizationClearTest () throws Exception {
        try {
            String authToken = authorizationObject.createAuth("Gavin");

            authorizationObject.clear();

            Assertions.assertEquals(authorizationObject.getAuth(authToken).authToken(),null, "User Gavin was not cleared.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }
}
