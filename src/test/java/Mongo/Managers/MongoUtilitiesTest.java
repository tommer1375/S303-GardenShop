package Mongo.Managers;

import Generic.Utilities.ConnectType;
import Generic.Managers.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MongoUtilitiesTest {

    @Test
    public void enterGardenShop() {
        assertTrue(Utilities.enterGardenShop("IT-Flowers", ConnectType.MONGO));
        assertFalse(Utilities.enterGardenShop("Petunias", ConnectType.MONGO));
    }
}