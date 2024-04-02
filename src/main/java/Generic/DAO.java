package Generic;

import Mongo.Connectivity.classes.GardenShop;
import Mongo.Connectivity.classes.Products;
import Mongo.Connectivity.classes.Stock;
import Mongo.Connectivity.classes.Tickets;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public interface DAO {
//    Creation methods
    void createGardenShop(String name, ArrayList<Stock> stockList, double currentValue);
    void createStock(String store_id, ArrayList<Stock> newStockList);
    void createTicket(String store_id, List<Products> products, double total);

    //    Read methods
    List<GardenShop> readGardenShops();
    Document readGardenShop(String name);
    List<Stock> readShopStock(String gardenShop_id);
    List<Tickets> readTicketsFromEnteredStore(String store_id);

//    Update methods
    int updateStock(String store_id, Stock update);

//    Delete methods
    boolean deleteGardenShop(String store_id);
    int deleteSingleStock(String store_id, String stock_id);
    void deleteFullStock(String store_id);
}
