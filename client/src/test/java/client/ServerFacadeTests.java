package client;

import chess.ChessGame;
import model.Game;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.jupiter.api.*;
import results.*;
import server.Server;
import serverfacade.ServerFacade;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;


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
        serverFacade.logout(URI.create("http://localhost:8080/session"), result.getAuthToken());

        serverFacade.clear(URI.create("http://localhost:8080"));

        LoginResult newResult = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");

        Assertions.assertEquals(newResult, null, "Clear didn't delete all the users");

        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult newNewresult = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");
        ListGamesResult listGames = serverFacade.listGames(URI.create("http://localhost:8080/game"),newNewresult.getAuthToken());

        Assertions.assertEquals(listGames, null, "Not all games were deleted.");
    }

    @Test
    @Order(2)
    @DisplayName("createGameTest")
    public void createGameTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        ListGamesResult listGames = serverFacade.listGames(URI.create("http://localhost:8080/game"),result.getAuthToken());

        ArrayList<Game> listOfGames = (ArrayList<Game>) listGames.getGames();
        Collection<Game> testCollection = new ArrayList<>();
        for (Game listOfGame : listOfGames) {
            int gameID = listOfGame.gameID();
            String gameName = listOfGame.gameName();
            ChessGame chessGame = listOfGame.game();
            testCollection.add(new Game(gameID, null, null, gameName, chessGame));
        }

        Assertions.assertTrue(listGames.getGames().equals(testCollection),"Game wasn't created");
    }

    @Test
    @Order(3)
    @DisplayName("createGameFailTest")
    public void createGameFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        CreateGameResult result = serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", null);

        Assertions.assertNull(result,"Not null.");
    }

    @Test
    @Order(4)
    @DisplayName("joinGameTest")
    public void joinGameTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");
        serverFacade.register(URI.create("http://localhost:8080/user"),"kendall","kendall", "kendall");

        LoginResult kendallResult = serverFacade.login(URI.create("http://localhost:8080/session"),
                "kendall", "kendall");
        LoginResult loginResult = serverFacade.login(URI.create("http://localhost:8080/session"),
                "Gavin", "password");

        CreateGameResult createGameResult = serverFacade.createGame(URI.create("http://localhost:8080/game"),
                "game", loginResult.getAuthToken());

        JoinGameResult joinGameResult = serverFacade.joinGame(URI.create("http://localhost:8080/game"),
                ChessGame.TeamColor.WHITE, createGameResult.getGameID(), loginResult.getAuthToken());
        JoinGameResult kendallJoinGameResult = serverFacade.joinGame(URI.create("http://localhost:8080/game"),
                ChessGame.TeamColor.BLACK, createGameResult.getGameID(), kendallResult.getAuthToken());

        Assertions.assertNull(joinGameResult.getMessage(),"Gavin didn't successfully join game");
        Assertions.assertNull(kendallJoinGameResult.getMessage(),"Kendall didn't successfully join game");

        ListGamesResult listGames = serverFacade.listGames(URI.create("http://localhost:8080/game"),loginResult.getAuthToken());
        ArrayList<Game> listOfGames = (ArrayList<Game>) listGames.getGames();

        Assertions.assertEquals(listOfGames.get(0).whiteUsername(),"Gavin", "Wrong whiteUsername");
        Assertions.assertEquals(listOfGames.get(0).blackUsername(),"kendall", "Wrong whiteUsername");
    }

    @Test
    @Order(5)
    @DisplayName("joinGameFailTest")
    public void joinGameFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");
        serverFacade.register(URI.create("http://localhost:8080/user"),"kendall","kendall", "kendall");

        LoginResult kendallResult = serverFacade.login(URI.create("http://localhost:8080/session"),
                "kendall", "kendall");
        LoginResult loginResult = serverFacade.login(URI.create("http://localhost:8080/session"),
                "Gavin", "password");

        CreateGameResult createGameResult = serverFacade.createGame(URI.create("http://localhost:8080/game"),
                "game", loginResult.getAuthToken());

        JoinGameResult joinGameResult = serverFacade.joinGame(URI.create("http://localhost:8080/game"),
                ChessGame.TeamColor.WHITE, createGameResult.getGameID(), null);
        JoinGameResult kendallJoinGameResult = serverFacade.joinGame(URI.create("http://localhost:8080/game"),
                ChessGame.TeamColor.BLACK, null, kendallResult.getAuthToken());

        Assertions.assertNull(joinGameResult,"Gavin's attempt to join should be null");
        Assertions.assertNull(kendallJoinGameResult,"Kendall's attempt to join should be null");
    }

    @Test
    @Order(6)
    @DisplayName("listGamesTest")
    public void listGamesTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        ListGamesResult listGames = serverFacade.listGames(URI.create("http://localhost:8080/game"),result.getAuthToken());

        ArrayList<Game> listOfGames = (ArrayList<Game>) listGames.getGames();
        Collection<Game> testCollection = new ArrayList<>();
        for (Game listOfGame : listOfGames) {
            int gameID = listOfGame.gameID();
            String gameName = listOfGame.gameName();
            ChessGame chessGame = listOfGame.game();
            testCollection.add(new Game(gameID, null, null, gameName, chessGame));
        }

        Assertions.assertEquals(listOfGames.size(),3,"Didn't list all the games.");
        Assertions.assertTrue(listGames.getGames().equals(testCollection),"List of games doesn't match.");

    }

    @Test
    @Order(7)
    @DisplayName("listGamesFailTest")
    public void listGamesFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        ListGamesResult listGames = serverFacade.listGames(URI.create("http://localhost:8080/game"),null);

        Assertions.assertNull(listGames, "listGames should be null since it was unauthorized.");

    }

    @Test
    @Order(8)
    @DisplayName("loginTest")
    public void loginTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");

        Assertions.assertNotNull(result,"Should not be null");
        Assertions.assertNotNull(result.getAuthToken(),"authToken should not be null.");
        Assertions.assertNotNull(result.getUsername(),"Username should not be null.");
        Assertions.assertNull(result.getMessage(),"Message should be null.");
    }

    @Test
    @Order(9)
    @DisplayName("loginFailTest")
    public void loginFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),null, "password");
        Assertions.assertNull(result, "Should be null.");

        result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", null);
        Assertions.assertNull(result, "Should be null.");
    }

    @Test
    @Order(10)
    @DisplayName("logoutTest")
    public void logoutTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");

        serverFacade.logout(URI.create("http://localhost:8080/session"), result.getAuthToken());

        CreateGameResult createGameResult = serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        Assertions.assertNull(createGameResult,"Should be null because the user is logged out.");
    }

    @Test
    @Order(11)
    @DisplayName("logoutFailTest")
    public void logoutFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");

        serverFacade.logout(URI.create("http://localhost:8080/session"), null);

        CreateGameResult createGameResult = serverFacade.createGame(URI.create("http://localhost:8080/game"),"game", result.getAuthToken());
        Assertions.assertNotNull(createGameResult,"Should not be null because the user is still logged in.");
    }

    @Test
    @Order(12)
    @DisplayName("registerTest")
    public void registerTest(){
        ServerFacade serverFacade = new ServerFacade();
        RegisterResult registerResult = serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");

        Assertions.assertNull(registerResult.getMessage(),"Should be null if registration was successful.");

        LoginResult result = serverFacade.login(URI.create("http://localhost:8080/session"),"Gavin", "password");

        Assertions.assertNotNull(result,"Should not be null if registration was successful.");
    }

    @Test
    @Order(13)
    @DisplayName("registerFailTest")
    public void registerFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        RegisterResult registerResult = serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");
        RegisterResult secondRegisterResult = serverFacade.register(URI.create("http://localhost:8080/user"),"Gavin","password", "email");
        RegisterResult thirdRegisterResult = serverFacade.register(URI.create("http://localhost:8080/user"),null,"password", "email");

        Assertions.assertNull(secondRegisterResult,"Should be null if registration was unsuccessful.");
        Assertions.assertNull(thirdRegisterResult,"Should be null if registration was unsuccessful.");
    }

    @Test
    @Order(14)
    @DisplayName("observeGameTest")
    public void observeGameTest(){
        ServerFacade serverFacade = new ServerFacade();
        Assertions.assertTrue(serverFacade.observeGame(1111),"Should display two chessboards from" +
                "different color positions.");
    }

    @Test
    @Order(15)
    @DisplayName("observeGameFailTest")
    public void observeGameFailTest(){
        ServerFacade serverFacade = new ServerFacade();
        Assertions.assertFalse(serverFacade.observeGame(null),"Should not display two chessboards from" +
                "different color positions.");

    }
}
