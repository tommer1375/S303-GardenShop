package Mongo.Managers.Stores;

import Generic.Utilities.Input;
import Generic.classes.GardenShop;
import Generic.classes.Stock;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.stock.StockManager;
import Mongo.Managers.MongoUtilities;
import Mongo.Managers.Tickets.TicketManager;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GardenShopManager {
    public static void createGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");

        ArrayList<Stock> stock = StockManager.createShopStock();

        double currentStockValue = MongoUtilities.getCurrentStockValue(stock);

        MongoDAO.INSTANCE.createGardenShop(name, stock, currentStockValue);
    }
    public static GardenShop createGardenShopFromDocument(Document document){


        return new GardenShop.Builder()
                ._id(document.getObjectId("_id").toString())
                .name(document.getString("name"))
                .stockList(document.getList("stock"))
    }
    public static void readActiveGardenShops(){
        List<GardenShop> activeGardenShops = MongoDAO.INSTANCE.readGardenShops();
        String activeGardenShopPrintable = "Current Active Garden Shops:";

        if (activeGardenShops.isEmpty()){
            activeGardenShopPrintable = activeGardenShopPrintable.concat("\n- Empty");
        } else {
            for (Document document : activeGardenShops){
                activeGardenShopPrintable = activeGardenShopPrintable.concat(MongoUtilities.extractGardenShopDescription(document));
            }
        }
        System.out.println(activeGardenShopPrintable);
    }
    public static void enterGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop whose Management System you'd like to enter.");

        boolean isInDatabase = MongoUtilities.enterGardenShop(name);

        if (!isInDatabase){
            System.out.println("That garden shop isn't registered in our system.");
            return;
        }

        while(true){
            switch (Input.readInt(EnteredGardenShop.INSTANCE
                    + """
                        0. Exit store.
                        1. See current Stock.
                        2. Update Stock.
                        3. See Past Tickets.
                        4. Create Ticket.
                        """)){
                case 0 -> {return;}
                case 1 -> StockManager.readStock();
                case 2 -> StockManager.updateStock();
                case 3 -> TicketManager.readPastTickets();
                case 4 -> TicketManager.createTicket();
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
