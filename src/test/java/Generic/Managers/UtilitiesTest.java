package Generic.Managers;

import Generic.Utilities.ConnectType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    @Test
    public void enterGardenShop() {
        assertTrue(Utilities.enterGardenShop("IT-Flowers", ConnectType.MONGO));
        assertFalse(Utilities.enterGardenShop("Petunias", ConnectType.MONGO));
        assertTrue(Utilities.enterGardenShop("Test1", ConnectType.MySQL));
        assertFalse(Utilities.enterGardenShop("Test0", ConnectType.MySQL));
    }
}