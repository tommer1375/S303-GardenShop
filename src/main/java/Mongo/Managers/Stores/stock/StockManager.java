package Mongo.Managers.Stores.stock;

import Generic.Utilities.Input;
import Generic.Utilities.MongoUtilities;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Stores.stock.qualities.Types;
import Mongo.Managers.Stores.stock.qualities.Error;
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
    public static Document createStockDocument(){
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
    public static void readStock(){
        switch (Input.readInt("""
                How would you like to see the stock:
                1. In full.
                2. In quantity.
                """)){
            case 1 -> System.out.println(EnteredGardenShop.INSTANCE.readStockInFull());
            case 2 -> System.out.println(EnteredGardenShop.INSTANCE.readStockInQuantities());
            default -> System.out.println("Invalid choice.");
        }
    }
    public static void updateStock(){
        switch (Input.readInt("""
                What would you like to do with the stock?
                1. Replace stock (eliminate and create a new one, empty or not).
                2. Add item to stock.
                3. Modify item from stock.
                4. Remove item from stock.
                """)){
            case 1 -> EnteredGardenShop.INSTANCE.updateFullStock();
            case 2 -> EnteredGardenShop.INSTANCE.createToStock();
            case 3 -> EnteredGardenShop.INSTANCE.updateItemFromStock();
            case 4 -> EnteredGardenShop.INSTANCE.deleteItemFromStock();
            default -> System.out.println("Invalid choice.");
        }
    }
    public static Document updateStockDocument(Document stock){
        boolean isModifyQuantity = Input.readIfNo("Would you like to modify the quantity?");
        if (isModifyQuantity) {
            int newQuantity = Input.readInt("Introduce new quantity.");
            stock.put("quantity", newQuantity);
        }

        boolean isModifyPrice = Input.readIfNo("Would you like to modify the price?");
        if (isModifyPrice){
            double newPrice = Input.readDouble("Introduce new price");
            stock.put("price", newPrice);
        }

        if(!isModifyPrice && !isModifyQuantity){
            System.out.println("No modifiable quality has been modified." +
                    "\nIf any of the other parts of the document are to be modified, eliminate this one, if need be, and create a new one.");
        }

        return stock;
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
}
