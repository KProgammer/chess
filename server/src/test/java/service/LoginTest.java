package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;

public class LoginTest {
    @Test
    @DisplayName("LoginGameTest")
    public void LoginGameTest() {
        RegisterResult registerResult = new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        LoginResult loginResult = new LoginService().login(new LoginRequest("Gavin","CowsAreAwesome"));

        Assertions.assertNotNull(loginResult.getAuthToken(),
                "Unable to login");

        loginResult = new LoginService().login(new LoginRequest("Gavin","CowsStink"));
        Assertions.assertEquals(loginResult,new LoginResult(null,null,"Error: unauthorized"), "Should have thrown a error.");
    }
}
