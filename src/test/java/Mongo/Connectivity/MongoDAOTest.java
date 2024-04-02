package Mongo.Connectivity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MongoDAOTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void createCaptor(){
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void resetCaptor(){
        System.setOut(System.out);
    }

//    Test works properly, but, due to the nature of ObjectIDs, it will never be true.
    @Test
    @Disabled
    void createGardenShop() {
        MongoDAO.INSTANCE.createGardenShop("TestCreateGardenShop()", new ArrayList<>(), 0);

        assertEquals("""
                Garden Shop Properly Created:
                - Store_id: 66070b40ea045d790a5ad54d
                \t-Name: TestCreateGardenShop()
                \t-Current Stock:\s
                \t-Current Stock Value: 0.0€
                \t-Current Sales Value: 0.0€""", outputStream.toString().trim());
    }

    @Test
    void createStock() {
    }

    @Test
    void createSingleStock() {
    }

    @Test
    void createTicket() {
    }

    @Test
    void readGardenShops() {
    }

    @Test
    void readGardenShop() {
    }

    @Test
    void readShopStock() {
    }

    @Test
    void readTicketsFromEnteredStore() {
    }

    @Test
    void updateStock() {
    }

    @Test
    void updateCurrentStockValue() {
    }

    @Test
    void updateCurrentSalesValue() {
    }

    @Test
    void deleteGardenShop() {
    }

    @Test
    void deleteSingleStock() {
    }

    @Test
    void deleteFullStock() {
    }
}