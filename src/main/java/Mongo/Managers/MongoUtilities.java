package Mongo.Managers;

import Generic.Utilities.Input;
import Generic.classes.Stock;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Stores.stock.qualities.*;
import Mongo.Managers.Stores.stock.qualities.Error;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoUtilities {
    public static String extractDocumentDescription(Document document) {
        StringBuilder description = new StringBuilder();
        extractFields(document, "", description);
        return description.toString();
    }
    private static void extractFields(Document document, String indent, StringBuilder description) {
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Document) {
                description.append(indent).append(fieldName).append(":\n");
                extractFields((Document) value, indent + "\t", description);
            } else if (value instanceof List) {
                description.append(indent).append(fieldName).append(": [\n");
                for (Object item : (List<?>) value) {
                    if (item instanceof Document) {
                        extractFields((Document) item, indent + "\t", description);
                    } else {
                        description.append(indent).append("\t").append(item).append("\n");
                    }
                }
                description.append(indent).append("]\n");
            } else {
                description.append(indent).append(fieldName).append(": ").append(value).append("\n");
            }
        }
    }
    public static double getCurrentStockValue(ArrayList<Stock> stockList){
        return stockList.stream()
                .mapToDouble(Stock::getPrice)
                .sum();
    }
    public static boolean enterGardenShop(String name){
        Document currentShop = MongoDAO.INSTANCE.readGardenShop(name);
        if (currentShop == null){
            return false;
        }

        String _id = currentShop.getObjectId("_id").toString();
        double currentStockValue = currentShop.getDouble("current_stock_value");
        double currentSalesValue = currentShop.getDouble("current_sales_value");

        EnteredGardenShop.INSTANCE.enter(_id, name, currentStockValue, currentSalesValue);
        return true;
    }
    public static Quality chooseHeight(){
        return switch (Input.readInt("""
                Choose the height for your tree:
                1. Small.
                2. Medium.
                3. Tall.
                """)){
            case 1 -> Height.SMALL;
            case 2-> Height.MEDIUM;
            case 3 -> Height.TALL;
            default -> Error.TREE;
        };
    }
    public static Quality chooseColor(){
        return switch (Input.readInt("""
                Choose the color for your flower:
                1. Red.
                2. Orange
                3. Yellow.
                4. Blue.
                5. Indigo.
                6. Violet.
                7. White.
                8. Pink.
                """)){
            case 1 -> Color.RED;
            case 2 -> Color.ORANGE;
            case 3 -> Color.YELLOW;
            case 4 -> Color.GREEN;
            case 5 -> Color.BLUE;
            case 6 -> Color.INDIGO;
            case 7 -> Color.VIOLET;
            case 8 -> Color.WHITE;
            case 9 -> Color.PINK;
            default -> Error.FLOWER;
        };
    }
    public static Quality chooseDecoration(){
        int choice = Input.readInt("""
                Choose the type of decoration for your tree:
                1. Wood.
                2. Plastic.
                """);
        return switch (choice){
            case 1 -> Material.WOOD;
            case 2 -> Material.PLASTIC;
            default -> Error.DECORATION;
        };
    }
}
