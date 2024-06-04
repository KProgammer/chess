package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class UserClearTest {
    @Test
    @DisplayName("UserClearsTest")
    public void UserClearsTest () throws Exception {
        try {
            userObject.createUser("Gavin","CowsAreAwesome","IloveCows@aol.com");
            userObject.createUser("Lance","NorwayIsAwesome","IloveNorway@aol.com");
            userObject.createUser("Kendall","RocketsAreAwesome","IloveRockets@aol.com");

            userObject.clear();

            Assertions.assertEquals(userObject.getUser("Gavin").username(),null, "User Gavin was not cleared.");
            Assertions.assertEquals(userObject.getUser("Lance").password(),null, "User Lance was not cleared.");
            Assertions.assertEquals(userObject.getUser("Kendall").email(),null, "User Kendall was not cleared.");

        } catch (Exception e){
            System.out.println("Threw Runtime Error in AuthorizationClearTests");
            throw e;
        }
    }
}
