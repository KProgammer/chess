package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;

public class CreateAuthTest {
    @Test
    @Order(1)
    @DisplayName("CreateAuthorizationTest")
    public void CreateAuthorizationTest () throws Exception {
        try {
            authorizationObject.clear();
            String authToken = authorizationObject.createAuth("Gavin");

            Assertions.assertInstanceOf(String.class,authToken,"Authorization didn't return an authToken.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAuthorizationTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("CreateAuthorizationFailTest")
    public void CreateAuthorizationFailTest () throws Exception {
        try {
            String authToken = authorizationObject.createAuth(null);
            Assertions.fail();
        } catch (Exception e){
            Assertions.assertEquals("Username was invalid.",e.getMessage(), "Didn't throw exception.");
        }
    }
}
