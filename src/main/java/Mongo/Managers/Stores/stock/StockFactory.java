package Mongo.Managers.Stores.stock;

import Generic.Utilities.Input;
import Mongo.Managers.Stores.stock.qualities.Types;
import Mongo.Managers.Stores.stock.qualities.Color;
import Mongo.Managers.Stores.stock.qualities.Decoration;
import Mongo.Managers.Stores.stock.qualities.Error;
import Mongo.Managers.Stores.stock.qualities.Height;
import Mongo.Managers.Stores.stock.qualities.Quality;
import org.bson.Document;

import java.util.ArrayList;

public class StockFactory {
    public static ArrayList<Document> createStock(){
        boolean isStockFilled = Input.readIfNo("Would you like to introduce any stock as of this moment?");
        ArrayList<Document> stockList;
        if(isStockFilled){
            stockList = fillStock();
        } else {
            stockList = new ArrayList<>();
        }

        return stockList;
    }
    private static ArrayList<Document> fillStock(){
        ArrayList<Document> documentArrayList = new ArrayList<>();
        int quantity = Input.readInt("How many items would you like to add to the stock?");

        while (quantity > 0){
            documentArrayList.add(createStockDocument());
            quantity--;
        }
        return documentArrayList;
    }
    private static Document createStockDocument(){
        boolean isCorrect = false;

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
            case TREE -> quality = chooseHeight();
            case FLOWER ->  quality = chooseColor();
            case DECORATION -> quality = chooseDecoration();
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
    private static Quality chooseHeight(){
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
    private static Quality chooseColor(){
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
    private static Quality chooseDecoration(){
        int choice = Input.readInt("""
                Choose the type of decoration for your tree:
                1. Wood.
                2. Plastic.
                """);
        return switch (choice){
            case 1 -> Decoration.WOOD;
            case 2 -> Decoration.PLASTIC;
            default -> Error.DECORATION;
        };
    }
}
