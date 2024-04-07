package Generic.Managers;

import Generic.Utilities.ConnectType;
import Generic.Utilities.Input;
import Generic.classes.GardenShop;
import Generic.classes.qualities.Error;
import Generic.classes.qualities.*;
import Mongo.Connectivity.MongoDAO;
import SQL.Connectivity.MySQLDAO;

public class Utilities {
    public static boolean enterGardenShop(String name, ConnectType connectType){
        GardenShop currentShop = switch (connectType){
            case MONGO -> MongoDAO.INSTANCE.readGardenShop(name);
            case MySQL -> MySQLDAO.INSTANCE.readGardenShop(name);
            case CHOOSE -> null;
        };
        if (currentShop == null){
            return false;
        }

        currentShop.enterGardenShop(connectType);
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
