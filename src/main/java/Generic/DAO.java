package Generic;

import Generic.classes.Tickets;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public interface DAO {
//    Creation methods
    void createGardenShop(String name, ArrayList<Document> stock, double currentValue);
    void createStock(Document filter, List<Document> newStockList);
    void createTicket();

//    Read methods
    List<Document> readGardenShops();
    Document readGardenShop(String name);
    List<Document> readShopStock(Document document);
    String readShopValue();
    String readSalesValue();
    List<Tickets> readOldPurchases();

//    Update methods
    void updateGardenShop();
    int updateStock(Document filter, Document update);

//    Delete methods
    void deleteGardenShop();
    int deleteSingleStock(Document filter);
    void deleteFullStock(Document filter);
}
