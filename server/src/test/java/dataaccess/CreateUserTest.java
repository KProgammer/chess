package dataaccess;

import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static server.Server.gameObject;
import static server.Server.userObject;

public class CreateUserTest {
    @Test
    @Order(1)
    @DisplayName("CreateAUserTest")
    public void CreateAUserTest () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Lance","Practice24","email@norway.com");

            Assertions.assertEquals(userObject.getUser("Lance").username(),"Lance",
                    "Username doesn't match.");
            Assertions.assertEquals("Practice24", userObject.getUser("Lance").password(),
                    "Password doesn't match");
            Assertions.assertEquals(userObject.getUser("Lance").email(),"email@norway.com",
                    "Email doesn't match.");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAUserTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("CreateAUserFailTest")
    public void CreateAUserFailTest (){
        try {
            userObject.createUser(null,"WierdAl","email");
            Assertions.fail();
        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAUserTest");
            Assertions.assertEquals("input was invalid.",e.getMessage(), "Didn't throw exception.");
        }
    }

    @Test
    @Order(3)
    @DisplayName("CreateAUserFailTest2")
    public void CreateAUserFailTest2 (){
        try {
            userObject.createUser("username",null,"email");
            Assertions.fail();
        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAUserTest");
            Assertions.assertEquals("input was invalid.",e.getMessage(), "Didn't throw exception.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("CreateAUserFailTest2")
    public void CreateAUserFailTest3 (){
        try {
            userObject.createUser(null,"WierdAl","email");
            Assertions.fail();
        } catch (Exception e){
            System.out.println("Threw Runtime Error in CreateAUserTest");
            Assertions.assertEquals("input was invalid.",e.getMessage(), "Didn't throw exception.");
        }
    }

}
