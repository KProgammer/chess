package dataaccess;

import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.gameObject;

public class CreateGameTest {
    @Test
    @Order(1)
    @DisplayName("CreateAGameTest")
    public void CreateAGameTest () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(1111,"Practice24");

            Game testGame = gameObject.getGame(1111);

            Assertions.assertInstanceOf(Game.class,testGame,"Game wasn't created.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAuthorizationTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("CreateAGameFailTest")
    public void CreateAGameFailTest () throws Exception {
        try {
            gameObject.createGame(1111,null);
            Assertions.fail();
        } catch (Exception e){
            Assertions.assertEquals("gameName was invalid.",e.getMessage(), "Didn't throw exception.");
        }
    }

    @Test
    @Order(3)
    @DisplayName("CreateAGameFailTest2")
    public void CreateAGameFailTest2 (){
        try {
            gameObject.createGame(null,"practice24");
            Assertions.fail();
        } catch (Exception e){
            Assertions.assertEquals("""
                            Cannot invoke "java.lang.Integer.intValue()" because "gameID" is null""",
                    e.getMessage(),
                    "Didn't throw exception.");
        }
    }
}
