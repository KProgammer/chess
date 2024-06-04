package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static server.Server.gameObject;

public class GameCreateStatementTest {
    @Test
    @Order(1)
    @DisplayName("CreateStatementTest")
    public void CreateStatementTest (){
        try {
            String[] statement = gameObject.createStatement();

            String[] testStatement = {"""
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` SMALLINT(255),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `ChessGame` TEXT,
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """};

            Boolean Test = Arrays.equals(statement, testStatement);

            Assertions.assertEquals(Test, true,"Statement was not creating the correct table.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in GameCreateStatementTests");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("CreateStatementFailTest")
    public void CreateStatementFailTest (){
        try {
            String[] statement = gameObject.createStatement();

            Assertions.assertNotNull(statement, "User was null when it should contain a String[].");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in GameCreateStatementTests");
            throw e;
        }
    }
}
