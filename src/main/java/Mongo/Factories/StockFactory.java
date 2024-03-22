package Mongo.Factories;

import Generic.Input;
import Generic.classes.Stock;
import Generic.classes.Types;
import Mongo.src.code.qualities.Color;
import Mongo.src.code.qualities.Decoration;
import Mongo.src.code.qualities.Error;
import Mongo.src.code.qualities.Height;
import Mongo.src.code.qualities.Quality;

public class StockFactory {
    public static Stock createStock(){
        String typeText = Input.readString("Introduce the type of stock you'd like to add.");
        Types type;
        try{
            type = Types.valueOf(typeText);
        } catch (IllegalArgumentException e){
            System.out.println("Invalid choice.");
            return null;
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

        return new Stock(type, price, quantity, quality);
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
            case 4 -> Color.BLUE;
            case 5 -> Color.INDIGO;
            case 6 -> Color.VIOLET;
            case 7 -> Color.WHITE;
            case 8 -> Color.PINK;
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
