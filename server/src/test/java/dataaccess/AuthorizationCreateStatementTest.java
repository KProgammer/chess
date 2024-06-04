package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static server.Server.authorizationObject;

public class AuthorizationCreateStatementTest {
    @Test
    @Order(1)
    @DisplayName("AuthCreateStatementTest")
    public void AuthCreateStatementTest (){
        try {
            String[] statement = authorizationObject.createStatement();

            String[] testStatement = {"""
            CREATE TABLE IF NOT EXISTS  authorization (
              `userName` varchar(256),
              `authToken` varchar(256),
              INDEX(userName),
              INDEX(authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """};

            Boolean test = Arrays.equals(statement, testStatement);

            Assertions.assertEquals(test, true,"Statement was not creating the correct table.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("AuthCreateStatementFailTest")
    public void authCreateStatementFailTest (){
        try {
            String[] statement = authorizationObject.createStatement();

            Assertions.assertNotNull(statement, "User was null when it should contain a String[].");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }
}
