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
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @BeforeEach
    public void clear()
    {
        facade.clear();
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
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");
        facade.createGame("game", result.getAuthToken());
        facade.logout(result.getAuthToken());

        facade.clear();

        LoginResult newResult = facade.login("Gavin", "password");

        Assertions.assertEquals(newResult, null, "Clear didn't delete all the users");

        facade.register("Gavin","password", "email");
        LoginResult newNewresult = facade.login("Gavin", "password");
        ListGamesResult listGames = facade.listGames(newNewresult.getAuthToken());

        Assertions.assertEquals(listGames.getGames().size(), 0, "Not all games were deleted.");
    }

    @Test
    @Order(2)
    @DisplayName("createGameTest")
    public void createGameTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");
        facade.createGame("game", result.getAuthToken());
        ListGamesResult listGames = facade.listGames(result.getAuthToken());

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
        CreateGameResult result = facade.createGame("game", null);

        Assertions.assertNull(result,"Not null.");
    }

    @Test
    @Order(4)
    @DisplayName("joinGameTest")
    public void joinGameTest(){
        facade.register("Gavin","password", "email");
        facade.register("kendall","kendall", "kendall");

        LoginResult kendallResult = facade.login("kendall", "kendall");
        LoginResult loginResult = facade.login("Gavin", "password");

        CreateGameResult createGameResult = facade.createGame("game", loginResult.getAuthToken());

        JoinGameResult joinGameResult = facade.joinGame(ChessGame.TeamColor.WHITE, createGameResult.getGameID(), loginResult.getAuthToken());
        JoinGameResult kendallJoinGameResult = facade.joinGame(ChessGame.TeamColor.BLACK, createGameResult.getGameID(), kendallResult.getAuthToken());

        Assertions.assertNull(joinGameResult.getMessage(),"Gavin didn't successfully join game");
        Assertions.assertNull(kendallJoinGameResult.getMessage(),"Kendall didn't successfully join game");

        ListGamesResult listGames = facade.listGames(loginResult.getAuthToken());
        ArrayList<Game> listOfGames = (ArrayList<Game>) listGames.getGames();

        Assertions.assertEquals(listOfGames.get(0).whiteUsername(),"Gavin", "Wrong whiteUsername");
        Assertions.assertEquals(listOfGames.get(0).blackUsername(),"kendall", "Wrong whiteUsername");
    }

    @Test
    @Order(5)
    @DisplayName("joinGameFailTest")
    public void joinGameFailTest(){
        facade.register("Gavin","password", "email");
        facade.register("kendall","kendall", "kendall");

        LoginResult kendallResult = facade.login("kendall", "kendall");
        LoginResult loginResult = facade.login("Gavin", "password");

        CreateGameResult createGameResult = facade.createGame("game", loginResult.getAuthToken());

        JoinGameResult joinGameResult = facade.joinGame(ChessGame.TeamColor.WHITE, createGameResult.getGameID(), null);
        JoinGameResult kendallJoinGameResult = facade.joinGame(ChessGame.TeamColor.BLACK, null, kendallResult.getAuthToken());

        Assertions.assertNull(joinGameResult,"Gavin's attempt to join should be null");
        Assertions.assertNull(kendallJoinGameResult,"Kendall's attempt to join should be null");
    }

    @Test
    @Order(6)
    @DisplayName("listGamesTest")
    public void listGamesTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");
        facade.createGame("game", result.getAuthToken());
        facade.createGame("game", result.getAuthToken());
        facade.createGame("game", result.getAuthToken());
        ListGamesResult listGames = facade.listGames(result.getAuthToken());

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
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");
        facade.createGame("game", result.getAuthToken());
        facade.createGame("game", result.getAuthToken());
        facade.createGame("game", result.getAuthToken());
        ListGamesResult listGames = facade.listGames(null);

        Assertions.assertNull(listGames, "listGames should be null since it was unauthorized.");

    }

    @Test
    @Order(8)
    @DisplayName("loginTest")
    public void loginTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");

        Assertions.assertNotNull(result,"Should not be null");
        Assertions.assertNotNull(result.getAuthToken(),"authToken should not be null.");
        Assertions.assertNotNull(result.getUsername(),"Username should not be null.");
        Assertions.assertNull(result.getMessage(),"Message should be null.");
    }

    @Test
    @Order(9)
    @DisplayName("loginFailTest")
    public void loginFailTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login(null, "password");
        Assertions.assertNull(result, "Should be null.");

        result = facade.login("Gavin", null);
        Assertions.assertNull(result, "Should be null.");
    }

    @Test
    @Order(10)
    @DisplayName("logoutTest")
    public void logoutTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");

        facade.logout(result.getAuthToken());

        CreateGameResult createGameResult = facade.createGame("game", result.getAuthToken());
        Assertions.assertNull(createGameResult,"Should be null because the user is logged out.");
    }

    @Test
    @Order(11)
    @DisplayName("logoutFailTest")
    public void logoutFailTest(){
        facade.register("Gavin","password", "email");

        LoginResult result = facade.login("Gavin", "password");

        facade.logout(null);

        CreateGameResult createGameResult = facade.createGame("game", result.getAuthToken());
        Assertions.assertNotNull(createGameResult,"Should not be null because the user is still logged in.");
    }

    @Test
    @Order(12)
    @DisplayName("registerTest")
    public void registerTest(){
        RegisterResult registerResult = facade.register("Gavin","password", "email");

        Assertions.assertNull(registerResult.getMessage(),"Should be null if registration was successful.");

        LoginResult result = facade.login("Gavin", "password");

        Assertions.assertNotNull(result,"Should not be null if registration was successful.");
    }

    @Test
    @Order(13)
    @DisplayName("registerFailTest")
    public void registerFailTest(){
        RegisterResult registerResult = facade.register("Gavin","password", "email");
        RegisterResult secondRegisterResult = facade.register("Gavin","password", "email");
        RegisterResult thirdRegisterResult = facade.register(null,"password", "email");

        Assertions.assertNull(secondRegisterResult,"Should be null if registration was unsuccessful.");
        Assertions.assertNull(thirdRegisterResult,"Should be null if registration was unsuccessful.");
    }

    @Test
    @Order(14)
    @DisplayName("observeGameTest")
    public void observeGameTest(){
        Assertions.assertTrue(facade.observeGame(1111),"Should display two chessboards from" +
                "different color positions.");
    }

    @Test
    @Order(15)
    @DisplayName("observeGameFailTest")
    public void observeGameFailTest(){
        Assertions.assertFalse(facade.observeGame(null),"Should not display two chessboards from" +
                "different color positions.");

    }
}
