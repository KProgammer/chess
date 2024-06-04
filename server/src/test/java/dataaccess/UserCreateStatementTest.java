package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static server.Server.userObject;

public class UserCreateStatementTest {
    @Test
    @Order(1)
    @DisplayName("UserCreatStatementTest")
    public void userCreatStatementTest (){
        try {
            String[] statement = userObject.createStatement();

            String[] testStatement = {"""
            CREATE TABLE IF NOT EXISTS  user (
              `id` INT NOT NULL AUTO_INCREMENT,
              `username` varchar(256),
              `password` varchar(256),
              `email` varchar(256),
              PRIMARY KEY (id),
              INDEX(username),
              INDEX(password),
              INDEX(email)
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
    @DisplayName("UserCreatStatementFailTest")
    public void userCreatStatementFailTest (){
        try {
            String[] statement = userObject.createStatement();

            Assertions.assertNotNull(statement, "User was null when it should contain a String[].");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }
}
