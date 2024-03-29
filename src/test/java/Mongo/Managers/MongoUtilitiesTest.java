package Mongo.Managers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MongoUtilitiesTest {

    @Test
    public void enterGardenShop() {
        assertTrue(MongoUtilities.enterGardenShop("IT-Flowers"));
        assertFalse(MongoUtilities.enterGardenShop("Petunias"));
    }
}