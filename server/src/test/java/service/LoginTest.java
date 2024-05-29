package service;

import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;
import org.junit.jupiter.api.*;

public class LoginTest {

    @BeforeEach
    public void init(){
        RegisterResult registerResult = new RegisterService().register(new RegisterRequest("Gavin","CowsAreAwesome","cows@cows.com"));
    }

    @Test
    @Order(1)
    @DisplayName("LoginSuccessGameTest")
    public void loginSuccessGameTest() {
        LoginResult loginResult = new LoginService().login(new LoginRequest("Gavin","CowsAreAwesome"));

        Assertions.assertNotNull(loginResult.getAuthToken(),
                "Unable to login");
    }

    @Test
    @Order(2)
    @DisplayName("LoginGameTest")
    public void unauthorizedLoginGameTest() {
        LoginResult loginResult = new LoginService().login(new LoginRequest("Gavin","CowsStink"));

        Assertions.assertEquals(loginResult,new LoginResult(null,null,"Error: unauthorized"), "Should have thrown a error.");
    }
}
