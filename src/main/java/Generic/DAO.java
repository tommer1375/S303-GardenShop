package Generic;

import Generic.classes.GardenShop;
import Generic.classes.Products;
import Generic.classes.Stock;
import Generic.classes.Tickets;

import java.util.ArrayList;
import java.util.List;

public interface DAO {
//    Creation methods
    void createGardenShop(String name, ArrayList<Stock> stockList, double currentValue);
    void createStock(String store_id, ArrayList<Stock> newStockList);
    int createSingleStock(String store_id, Stock stock);
    void createTicket(String store_id, List<Products> products, double total);

    //    Read methods
    List<GardenShop> readGardenShops();
    GardenShop readGardenShop(String name);
    List<Stock> readShopStock(String gardenShop_id);
    List<Tickets> readTicketsFromEnteredStore(String store_id);

//    Update methods
    int updateStock(String store_id, Stock update);
    void updateCurrentStockValue(String store_id, double newStockValue);
    void updateCurrentSalesValue(String store_id, double newSalesValue);

//    Delete methods
    boolean deleteGardenShop(String store_id);
    int deleteSingleStock(String store_id, String stock_id);
    void deleteFullStock(String store_id);
}
