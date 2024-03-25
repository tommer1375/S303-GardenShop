package Generic;

import Generic.ConnectType;
import Generic.Utilities.Input;
import Mongo.Managers.Stores.GardenShopManager;

public class Helper {
    @SuppressWarnings("SameParameterValue")
    protected static void connectWith(ConnectType connectType){
        switch (connectType){
            case MySQL -> connectToMySQL();
            case MONGO -> connectToMongo();
            case CHOOSE -> System.out.println("Please choose a connection type before turning on the machine.");
        }
    }
    private static void connectToMongo(){
        while(true){
            switch (Input.readInt("""
                    Welcome to our GardenShopManager™! Choose your option:
                    1. Create Garden Shop.
                    2. See Currently Active Garden Shops.
                    3. Enter a Garden Shop's internal management systems.
                    4. Exit.
                    """)){
                case 1:
                    GardenShopManager.createGardenShop();
                    break;
                case 2:
                    GardenShopManager.readActiveGardenShops();
                    break;
                case 3:
                    GardenShopManager.enterGardenShop();
                    break;
                case 4:
                    System.out.println("Thank you for your hard work, see you next time.");
                    System.exit(0);
                default:
                    System.out.println("Unfortunately, that option isn't included in our system.");
            }
        }
    }
    private static void connectToMySQL(){

    }
}
