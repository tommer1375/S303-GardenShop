package Generic;

import Generic.classes.GardenShop;
import Generic.classes.Products;
import Generic.classes.Tickets;

import java.util.List;

public interface DAO {
//    Creation and modification methods
    void addGardenShop();
    void modifyGardenShop();
    void removeGardenShop();
    void addStock();
    void modifyStock();
    void removeStock();
    void addTicket();

//    Visualization methods
    List<GardenShop> seeGardenShops();
    List<Products> seeShopStock();
    String seeShopValue();
    String seeSalesValue();
    List<Tickets> seeOldPurchases();
}
