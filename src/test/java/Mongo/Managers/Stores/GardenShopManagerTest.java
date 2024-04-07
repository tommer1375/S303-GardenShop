package Mongo.Managers.Stores;

import Generic.Utilities.ConnectType;
import Generic.Managers.Stores.GardenShopManager;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

//Test only valid at 29/03/2024, as the database content will be modified after this. Result, positive
@Nested
class GardenShopManagerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void createCaptor(){
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void resetCaptor(){
        System.setOut(System.out);
    }

    @Test
    @Disabled
    void readActiveGardenShops() {
        GardenShopManager.readActiveGardenShops(ConnectType.MONGO);

        assertEquals("""
                Current Active Garden Shops:
                - Store_id: 6606d0bf43cc842e6d82efa1
                	-Name: IT-Flowers
                	-Current Stock:\s
                		- Product_id: 6606cfe643cc842e6d82ef90
                			Type: TREE
                			Price: 15.0€
                			Quantity: 1000
                			Height: SMALL
                		- Product_id: 6606cff143cc842e6d82ef91
                			Type: TREE
                			Price: 50.0€
                			Quantity: 1000
                			Height: MEDIUM
                		- Product_id: 6606d00243cc842e6d82ef92
                			Type: TREE
                			Price: 200.0€
                			Quantity: 1000
                			Height: TALL
                		- Product_id: 6606d02b43cc842e6d82ef93
                			Type: FLOWER
                			Price: 1.0€
                			Quantity: 1000
                			Color: RED
                		- Product_id: 6606d03e43cc842e6d82ef94
                			Type: FLOWER
                			Price: 2.0€
                			Quantity: 1000
                			Color: ORANGE
                		- Product_id: 6606d04c43cc842e6d82ef95
                			Type: FLOWER
                			Price: 4.0€
                			Quantity: 1000
                			Color: YELLOW
                		- Product_id: 6606d05843cc842e6d82ef96
                			Type: FLOWER
                			Price: 8.0€
                			Quantity: 1000
                			Color: GREEN
                		- Product_id: 6606d06c43cc842e6d82ef97
                			Type: FLOWER
                			Price: 16.0€
                			Quantity: 1000
                			Color: BLUE
                		- Product_id: 6606d07843cc842e6d82ef98
                			Type: FLOWER
                			Price: 32.0€
                			Quantity: 1000
                			Color: INDIGO
                		- Product_id: 6606d08243cc842e6d82ef99
                			Type: FLOWER
                			Price: 64.0€
                			Quantity: 1000
                			Color: VIOLET
                		- Product_id: 6606d08f43cc842e6d82ef9a
                			Type: FLOWER
                			Price: 128.0€
                			Quantity: 1000
                			Color: WHITE
                		- Product_id: 6606d09d43cc842e6d82ef9b
                			Type: DECORATION
                			Price: 15.0€
                			Quantity: 1000
                			Material: WOOD
                		- Product_id: 6606d0ac43cc842e6d82ef9c
                			Type: DECORATION
                			Price: 30.0€
                			Quantity: 1000
                			Material: WOOD
                		- Product_id: 6606d0b543cc842e6d82ef9d
                			Type: DECORATION
                			Price: 5.0€
                			Quantity: 1000
                			Material: PLASTIC
                		- Product_id: 6606d0bf43cc842e6d82ef9e
                			Type: TREE
                			Price: 132568.0€
                			Quantity: 1
                			Height: TALL
                	-Current Stock Value: 133138.0€
                	-Current Sales Value: 0.0€
                - Store_id: 6606d6748d23e427640f031d
                	-Name: Test1
                	-Current Stock:\s
                	-Current Stock Value: 1500000.0€
                	-Current Sales Value: 0.0€
                - Store_id: 6606f3e4bc32641f26393325
                	-Name: Test2
                	-Current Stock:\s
                		- Product_id: 6606f3d7bc32641f26393322
                			Type: TREE
                			Price: 1000.0€
                			Quantity: 15
                			Height: TALL
                		- Product_id: 6606f3e4bc32641f26393323
                			Type: FLOWER
                			Price: 25.0€
                			Quantity: 100
                			Color: INDIGO
                	-Current Stock Value: 17500.0€
                	-Current Sales Value: 0.0€""", outputStream.toString().trim());
    }
}