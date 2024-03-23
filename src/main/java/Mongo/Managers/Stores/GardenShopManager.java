package Mongo.Managers.Stores;

import Generic.Utilities.Input;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.stock.StockManager;
import Generic.Utilities.MongoUtilities;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GardenShopManager {
    public static void createGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");

        ArrayList<Document> stock = StockManager.createShopStock();

        double currentValue = MongoUtilities.getCurrentValue(stock);

        MongoDAO.INSTANCE.createGardenShop(name, stock, currentValue);
    }
    public static void seeActiveGardenShops(){
        List<Document> activeGardenShops = MongoDAO.INSTANCE.readGardenShops();
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
            switch (Input.readInt(EnteredGardenShop.INSTANCE.toString()
                    + """
                        0. Exit store.
                        1. See current Stock.
                        2. Update Stock.
                        """)){
                case 0 -> {return;}
                case 1 -> System.out.println(EnteredGardenShop.INSTANCE.readStock());

            }
        }
    }
}
