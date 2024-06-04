package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.userObject;

public class GetUserTest {
    @Test
    @Order(1)
    @DisplayName("GetAUserTest")
    public void getAUserTest () throws Exception {
        try {
            userObject.clear();
            userObject.createUser("Wierdo","IamWierd","WierdosRule@wierd.com");

            Assertions.assertEquals(userObject.getUser("Wierdo").username(),"Wierdo",
                    "username should be Wierdo");
            Assertions.assertEquals(userObject.getUser("Wierdo").password(),"IamWierd",
                    "username should be IamWierd");
            Assertions.assertEquals(userObject.getUser("Wierdo").email(),"WierdosRule@wierd.com",
                    "username should be WierdosRule@wierd.com");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAUserTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("GetAUserFail")
    public void getAUserFail () throws Exception {
        try {
            userObject.getUser(null);
            Assertions.fail();
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAUserFail");
            Assertions.assertEquals("input was invalid.",e.getMessage(),"Failed to throw an exception.");
        }
    }
}
