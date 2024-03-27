package Mongo.Managers.Stores.stock;

import Generic.Utilities.Input;
import Generic.classes.Stock;
import Mongo.Managers.MongoUtilities;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Stores.stock.qualities.*;
import Mongo.Managers.Stores.stock.qualities.Error;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class StockManager {
    private static final Logger logger = LoggerFactory.getLogger(StockManager.class);
    public static ArrayList<Stock> createShopStock(){
        boolean isStockFilled = Input.readIfNo("Would you like to introduce any stock as of this moment?");
        ArrayList<Stock> stockList;
        if(isStockFilled){
            stockList = fillShopStock();
        } else {
            stockList = new ArrayList<>();
        }

        return stockList;
    }
    private static ArrayList<Stock> fillShopStock(){
        ArrayList<Stock> documentArrayList = new ArrayList<>();
        int quantity = Input.readInt("How many items would you like to add to the stock?");

        while (quantity > 0){
            Stock stock = createStock();

            if(stock != null){
                documentArrayList.add(stock);
                quantity--;
            }
        }
        return documentArrayList;
    }
    public static Stock createStock(){
        Types type;
        do {
            switch (Input.readInt("""
                Choose type:
                1. Tree.
                2. Flower.
                3. Decoration.
                """)){
                case 1 -> type = Types.TREE ;
                case 2 -> type = Types.FLOWER;
                case 3 -> type = Types.DECORATION;
                default -> type = Types.ERROR;
            }
        } while (type == Types.ERROR);

        double price = Input.readDouble("Introduce the price per unit.");
        int quantity = Input.readInt("Introduce how many there'll be in stock");

        Quality quality;
        switch (type){
            case TREE -> quality = MongoUtilities.chooseHeight();
            case FLOWER ->  quality = MongoUtilities.chooseColor();
            case DECORATION -> quality = MongoUtilities.chooseDecoration();
            default -> quality = Error.ERROR;
        }

        return new Stock.Builder()
                .product_id(new ObjectId().toString())
                .type(type)
                .price(price)
                .quantity(quantity)
                .quality(quality)
                .build();
    }
    public static Stock createStockFromDocument(Document document){
        Types type = Types.valueOf(document.getString("type").toUpperCase());
        Quality quality = switch (type){
            case TREE -> Height.valueOf(document.getString("Height"));
            case FLOWER -> Color.valueOf(document.getString("Color"));
            case DECORATION -> Material.valueOf(document.getString("Material"));
            default -> {
                logger.atInfo().log("Incorrect execution of quality assignment at createStockFromDocument() in:\n src/main/java/Mongo/Managers/Stores/stock/StockManager.java");
                throw new IllegalStateException("Unexpected value: " + type);
            }
        };

        return new Stock.Builder()
                .product_id(document.getObjectId("product_id").toString())
                .type(type)
                .price(document.getDouble("price"))
                .quantity(document.getInteger("quantity"))
                .quality(quality)
                .build();
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
    public static Stock updateStockDocument(Stock stock){
        boolean isModifyQuantity = Input.readIfNo("Would you like to modify the quantity?");
        if (isModifyQuantity) {
            int newQuantity = Input.readInt("Introduce new quantity.");
            stock.setQuantity(newQuantity);
        }

        boolean isModifyPrice = Input.readIfNo("Would you like to modify the price?");
        if (isModifyPrice){
            double newPrice = Input.readDouble("Introduce new price");
            stock.setPrice(newPrice);
        }

        if(!isModifyPrice && !isModifyQuantity){
            System.out.println("No modifiable quality has been modified." +
                    "\nIf any of the other parts of the document are to be modified, eliminate this one, if need be, and create a new one.");
        }

        return stock;
    }
}
