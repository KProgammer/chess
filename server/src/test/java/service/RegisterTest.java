package service;

import org.mindrot.jbcrypt.BCrypt;
import requests.RegisterRequest;
import results.RegisterResult;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.SequencedSet;

import static server.Server.userObject;

public class RegisterTest {

    @Test
    @Order(1)
    @DisplayName("RegisterGamesTest")
    public void registerGamesTest(){
        try {
            new RegisterService().register(new RegisterRequest("Gavin", "CowsAreAwesome", "cows@cows.com"));

            Assertions.assertEquals(userObject.getUser("Gavin").username(), "Gavin",
                    "User not registered.");
            Assertions.assertTrue(BCrypt.checkpw("CowsAreAwesome", userObject.getUser("Gavin").password()),
                    "User not registered.");
            Assertions.assertEquals(userObject.getUser("Gavin").email(), "cows@cows.com",
                    "User not registered.");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in registerGamesTest");
        }

    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedRegisterGamesTest")
    public void unauthorizedRegisterGamesTest(){
        try {
            new RegisterService().register(new RegisterRequest("Gavin", "CowsAreAwesome", "cows@cows.com"));

            Assertions.assertEquals(new RegisterService().register(new RegisterRequest("Gavin", "CowsAreAwesome", "cows@cows.com")),
                    new RegisterResult(null, null, "Error: already taken"), "Did not throw Error: already taken.");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in unauthorizedRegisterGamesTest");
        }
    }
}
