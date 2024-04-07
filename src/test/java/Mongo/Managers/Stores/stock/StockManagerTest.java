package Mongo.Managers.Stores.stock;

import Generic.Utilities.ConnectType;
import Generic.Managers.Utilities;
import Generic.Managers.Stores.stock.StockManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StockManagerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void enterGardenShopAndCreateCaptor(){
        Utilities.enterGardenShop("Test1", ConnectType.MONGO);
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void resetCaptor(){
        System.setOut(System.out);
    }
    @Test
    void readStock() {
        StockManager.readStock();

        assertEquals("Stock empty.", outputStream.toString().trim());
    }
}