package Mongo.Managers.Stores;

import Generic.Utilities.ConnectType;
import Generic.Managers.Utilities;
import Generic.Managers.Stores.EnteredGardenShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EnteredGardenShopTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void EnterGardenShopAndCreateCaptor(){
        Utilities.enterGardenShop("Test2", ConnectType.MONGO);
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void clearCaptor(){
        System.setOut(System.out);
    }
    @Test
    void readStockInFull() {
        assertEquals("""
                Current Stock:\s
                		- Product_id: 6606f3d7bc32641f26393322
                			Type: TREE
                			Price: 1000.0€
                			Quantity: 15
                			Height: TALL
                		- Product_id: 6606f3e4bc32641f26393323
                			Type: FLOWER
                			Price: 25.0€
                			Quantity: 100
                			Color: INDIGO""", EnteredGardenShop.INSTANCE.readStockInFull());
    }

    @Test
    void readStockInQuantities() {
        assertEquals("""
                Current Stock:
                - TREE: 1
                - FLOWER: 1""", EnteredGardenShop.INSTANCE.readStockInQuantities());
    }

    @Test
    void readTickets() {
        assertEquals("""
                Tickets from store, ID: 6606f3e4bc32641f26393325Ticket 6606feae04c7623687af9b66
                	Store ID: 6606f3e4bc32641f26393325
                	Products bought:
                		-Product_id: 6606f3e4bc32641f26393323
                		-Quantity: 15
                		-Total: 375.0
                	Total: 375.0€""", EnteredGardenShop.INSTANCE.readTickets());
    }

    @Test
    void get_id() {
        assertEquals("6606f3e4bc32641f26393325", EnteredGardenShop.INSTANCE.get_id());
    }

    @Test
    void getMatchingStockID() {
        assertEquals("""

                \t\t- Product_id: 6606f3e4bc32641f26393323
                \t\t\tType: FLOWER
                \t\t\tPrice: 25.0€
                \t\t\tQuantity: 100
                \t\t\tColor: INDIGO""", EnteredGardenShop.INSTANCE.getMatchingStock("6606f3e4bc32641f26393323").toString());
    }

    @Test
    void isStockListEmpty() {
        assertFalse(EnteredGardenShop.INSTANCE.isStockListEmpty());
    }

    @Test
    void testToString() {
        assertEquals("""
                Store_id: 6606f3e4bc32641f26393325
                \tName: Test2
                \tCurrent Stock Value: 17125.0
                \tCurrent Sales Value: 0.0
                """, EnteredGardenShop.INSTANCE.toString());
    }
}