package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogoutTest {
    @Test
    @DisplayName("LogoutGameTest")
    public void LogoutGameTest() {
        RegisterResult registerResult = new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));

        LogoutResult logoutResult = new LogoutService().logout(new LogoutRequest(registerResult.getAuthToken()));

        Assertions.assertEquals(logoutResult, new LogoutResult(null),
                    "Unable to logout");

        logoutResult = new LogoutService().logout(new LogoutRequest(null));
        Assertions.assertEquals(logoutResult,new LogoutResult("Error: unauthorized"), "Should have thrown a error.");
    }
}
