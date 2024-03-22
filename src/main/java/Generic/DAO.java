package Generic;

import Generic.classes.Products;
import Generic.classes.Tickets;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public interface DAO {
//    Creation methods
    void createGardenShop(String name, ArrayList<Document> stock, double currentValue);
    void createTicket();

//    Read methods
    List<Document> readGardenShops();
    List<Products> readShopStock();
    String readShopValue();
    String readSalesValue();
    List<Tickets> readOldPurchases();

//    Update methods
    void updateGardenShop();
    void updateStock();

//    Delete methods
    void deleteGardenShop();
    void deleteStock();
}
