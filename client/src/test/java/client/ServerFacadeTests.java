package client;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.jupiter.api.*;
import results.LoginResult;
import server.Server;
import serverfacade.ServerFacade;

import java.net.URI;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(1)
    @DisplayName("ClearTest")
    public void clearTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        serverFacade.logout(URI.create("http://localhost:8080"),)

        serverFacade.clear(URI.create("http://localhost:8080"));

        LoginResult newResult =
    }

    @Test
    @Order(2)
    @DisplayName("createGameTest")
    public void createGameTest(){

    }

    @Test
    @Order(3)
    @DisplayName("createGameFailTest")
    public void createGameFailTest(){

    }

    @Test
    @Order(4)
    @DisplayName("joinGameTest")
    public void joinGameTest(){

    }

    @Test
    @Order(5)
    @DisplayName("joinGameFailTest")
    public void joinGameFailTest(){

    }

    @Test
    @Order(6)
    @DisplayName("listGamesTest")
    public void listGamesTest(){

    }

    @Test
    @Order(7)
    @DisplayName("listGamesFailTest")
    public void listGamesFailTest(){

    }

    @Test
    @Order(8)
    @DisplayName("loginTest")
    public void loginTest(){

    }

    @Test
    @Order(9)
    @DisplayName("loginFailTest")
    public void loginFailTest(){

    }

    @Test
    @Order(10)
    @DisplayName("logoutTest")
    public void logoutTest(){

    }

    @Test
    @Order(11)
    @DisplayName("logoutFailTest")
    public void logoutFailTest(){

    }

    @Test
    @Order(12)
    @DisplayName("registerTest")
    public void registerTest(){

    }

    @Test
    @Order(13)
    @DisplayName("registerFailTest")
    public void registerFailTest(){

    }

    @Test
    @Order(14)
    @DisplayName("observeGameTest")
    public void observeGameTest(){

    }

    @Test
    @Order(15)
    @DisplayName("observeGameFailTest")
    public void observeGameFailTest(){

    }
}
