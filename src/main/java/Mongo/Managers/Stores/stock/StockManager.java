package Mongo.Managers.Stores.stock;

import Generic.Utilities.Input;
import Generic.Utilities.MongoUtilities;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Stores.stock.qualities.Types;
import Mongo.Managers.Stores.stock.qualities.Color;
import Mongo.Managers.Stores.stock.qualities.Decoration;
import Mongo.Managers.Stores.stock.qualities.Error;
import Mongo.Managers.Stores.stock.qualities.Height;
import Mongo.Managers.Stores.stock.qualities.Quality;
import org.bson.Document;

import java.util.ArrayList;

public class StockManager {
    public static ArrayList<Document> createShopStock(){
        boolean isStockFilled = Input.readIfNo("Would you like to introduce any stock as of this moment?");
        ArrayList<Document> stockList;
        if(isStockFilled){
            stockList = fillShopStock();
        } else {
            stockList = new ArrayList<>();
        }

        return stockList;
    }
    public static void updateStock(){
        switch (Input.readInt("""
                What would you like to do with the stock?
                1. Replace stock (eliminate and create a new one, empty or not).
                2. Add item to stock.
                3. Modify item from stock.
                4. Remove item from stock.
                """)){
            case 1 -> EnteredGardenShop.INSTANCE.replaceStock();
            case 2 -> EnteredGardenShop.INSTANCE.updateStock();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static ArrayList<Document> fillShopStock(){
        ArrayList<Document> documentArrayList = new ArrayList<>();
        int quantity = Input.readInt("How many items would you like to add to the stock?");

        while (quantity > 0){
            documentArrayList.add(createStockDocument());
            quantity--;
        }
        return documentArrayList;
    }
    private static Document createStockDocument(){
        Types type = Types.ERROR;

        while (type == Types.ERROR){
            switch (Input.readInt("""
                Choose type:
                1. Tree.
                2. Flower.
                3. Decoration.
                """)){
                case 1 -> type = Types.TREE ;
                case 2 -> type = Types.FLOWER;
                case 3 -> type = Types.DECORATION;
            }
        }

        double price = Input.readDouble("Introduce the price per unit.");

        int quantity = Input.readInt("Introduce how many there'll be in stock");

        Quality quality;
        switch (type){
            case TREE -> quality = MongoUtilities.chooseHeight();
            case FLOWER ->  quality = MongoUtilities.chooseColor();
            case DECORATION -> quality = MongoUtilities.chooseDecoration();
            default -> quality = Error.ERROR;
        }
        if(quality instanceof Error){
            System.out.println("Invalid choice.");
            return null;
        }

        return new Document()
                .append("type" , type.getDbValue())
                .append("price", price)
                .append("quantity", quantity)
                .append(quality.getClass().getSimpleName(), quality.getName());
    }
}
