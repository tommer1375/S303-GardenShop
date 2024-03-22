package Mongo.Factories;

import Generic.Input;
import Mongo.Connectivity.MongoDAO;
import org.bson.Document;

import java.util.ArrayList;

public class GardenShopFactory {
    public static void createGardenShop(){
        String name = Input.readString("Introduce the name of the Garden Shop you'd like to create.");

        ArrayList<Document> stock = fillStock();

        double currentValue = getCurrentValue(stock);

        MongoDAO.INSTANCE.createGardenShop(name, stock, currentValue);
    }
    private static ArrayList<Document> fillStock(){
        return StockFactory.createStock();
    }
    private static double getCurrentValue(ArrayList<Document> stock){
        double currentValue = 0;

        for (Document document : stock){
            currentValue += document.getDouble("price");
        }

        return currentValue;
    }
}
