package Generic.Managers.Stores.stock;

import Generic.Managers.Utilities;
import Generic.Managers.Stores.EnteredGardenShop;
import Generic.Utilities.Input;
import Generic.classes.Stock;
import Generic.classes.qualities.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class StockManager {
    private static final Logger logger = LoggerFactory.getLogger(StockManager.class);
    public static ArrayList<Stock> createShopStock(){
        boolean isStockFilled = Input.readBoolean("Would you like to introduce any stock as of this moment?");
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

        Quality quality = switch (type){
            case TREE -> Utilities.chooseHeight();
            case FLOWER -> Utilities.chooseColor();
            case DECORATION -> Utilities.chooseDecoration();
            default -> null;
        };

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
    @SuppressWarnings("unused")
    public static Stock createStockFromResultSet(ResultSet resultSet) {
        try {
            String product_id = resultSet.getString("product_id");
            Types type = Types.valueOf(resultSet.getString("type"));
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            Quality quality = switch (type){
                case TREE -> Height.valueOf(resultSet.getString("quality"));
                case FLOWER -> Color.valueOf(resultSet.getString("quality"));
                case DECORATION -> Material.valueOf(resultSet.getString("quality"));
                default -> null;
            };

            return new Stock.Builder()
                    .product_id(product_id)
                    .type(type)
                    .price(price)
                    .quantity(quantity)
                    .quality(quality)
                    .build();
        } catch (SQLException e) {
            getLogger(StockManager.class).atError().log("Unable to create connection at createGardenShop(), check connection settings." + "\n"
                    + "Error Message: " + e.getMessage() + "\n"
                    + "SQL State: " + e.getSQLState());
            return null;
        }
    }
    public static void readStock(){
        if(EnteredGardenShop.INSTANCE.isStockListEmpty()){
            System.out.println("Stock empty.");
            return;
        }
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
        switch (Input.readInt(EnteredGardenShop.INSTANCE.readStockInFull() +
                """
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
    public static Stock updateStockItem(Stock stock){
        boolean isModifyQuantity = Input.readBoolean("Would you like to modify the quantity?");
        if (isModifyQuantity) {
            int newQuantity = Input.readInt("Introduce new quantity.");
            stock.setQuantity(newQuantity);
        }

        boolean isModifyPrice = Input.readBoolean("Would you like to modify the price?");
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
