package Mongo.src;

import Generic.Input;
import Mongo.Factories.GardenShopFactory;

public class Helper {
    protected static void managerStart(){
        while(true){
            switch (Input.readInt("""
                    Welcome to our GardenShopManager™! Choose your option:
                    1. Create Garden Shop.
                    2. See Currently Active Garden Shops.
                    3. Enter a Garden Shop's internal management systems.
                    4. Exit.
                    """)){
                case 1:
                    createGardenShop();
                    break;
                case 2:
                    seeActiveGardenShops();
                    break;
                case 3:
                    enterGardenShop();
                    break;
                case 4:
                    System.out.println("Thank you for your hard work, see you next time.");
                    System.exit(0);
                default:
                    System.out.println("Unfortunately, that option isn't included in our system.");
            }
        }
    }
    private static void createGardenShop(){
        GardenShopFactory.createGardenShop();
    }
    private static void seeActiveGardenShops(){

    }
    private static void enterGardenShop(){

    }
}
