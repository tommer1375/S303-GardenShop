package Generic;

import Generic.classes.GardenShop;
import Generic.classes.Stock;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public interface DAO {
//    Creation methods
    void createGardenShop(String name, ArrayList<Stock> stockList, double currentValue);
    void createStock(Document filter, List<Document> newStockList);
    void createTicket(ObjectId _id, ObjectId store_id, List<Document> products, double total);

    //    Read methods
    List<GardenShop> readGardenShops();
    Document readGardenShop(String name);
    List<Document> readShopStock(Document document);
    List<Document> readPastTickets();

//    Update methods
    int updateStock(Document filter, Document update);

//    Delete methods
    void deleteGardenShop();
    int deleteSingleStock(Document filter);
    void deleteFullStock(Document filter);
}
