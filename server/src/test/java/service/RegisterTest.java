package service;

import requests.RegisterRequest;
import results.RegisterResult;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.userObject;

public class RegisterTest {

    @Test
    @Order(1)
    @DisplayName("RegisterGamesTest")
    public void registerGamesTest(){
        new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        Assertions.assertEquals(userObject.getUser("Gavin"), new User("Gavin","CowsAreAwesome","cows@cows.com"),
                "User not registered.");

    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedRegisterGamesTest")
    public void unauthorizedRegisterGamesTest(){
        new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        Assertions.assertEquals(new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com")),
                new RegisterResult(null, null, "Error: already taken"), "Did not throw Error: already taken.");

    }
}
