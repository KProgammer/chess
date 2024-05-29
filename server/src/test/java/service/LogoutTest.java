package service;

import Requests.LogoutRequest;
import Requests.RegisterRequest;
import Results.LogoutResult;
import Results.RegisterResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;

public class LogoutTest {
    @Test
    @Order(1)
    @DisplayName("LogoutGameTest")
    public void LogoutGameTest() {
        RegisterResult registerResult = new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        LogoutResult logoutResult = new LogoutService().logout(new LogoutRequest(registerResult.getAuthToken()));

        Assertions.assertEquals(authorizationObject.getAuth(registerResult.getAuthToken()), null,
                    "Unable to logout");
    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedLogoutGameTest")
    public void UnauthorizedLogoutGameTest() {
        RegisterResult registerResult = new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        LogoutResult logoutResult = new LogoutService().logout(new LogoutRequest(registerResult.getAuthToken()));

        logoutResult = new LogoutService().logout(new LogoutRequest(null));
        Assertions.assertEquals(logoutResult,new LogoutResult("Error: unauthorized"), "Should have thrown a error.");
    }
}
