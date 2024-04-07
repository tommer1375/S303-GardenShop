package Generic;

import Generic.Managers.Stores.GardenShopManager;
import Generic.Utilities.ConnectType;
import Generic.Utilities.Input;

import static org.slf4j.LoggerFactory.getLogger;

public class Helper {
    @SuppressWarnings("SameParameterValue")
    protected static void connectWith(ConnectType connectType) {
        getLogger(Helper.class).atInfo().log("Starting Application");
        if (connectType.equals(ConnectType.CHOOSE)){
            System.out.println("Please choose a connection type before turning on the machine.");
            return;
        }
        while (true) {
            switch (Input.readInt("""
                    Welcome to our GardenShopManager™! Choose your option:
                    1. Create Garden Shop.
                    2. See Currently Active Garden Shops.
                    3. Enter a Garden Shop's internal management systems.
                    4. Exit.
                    """)) {
                case 1:
                    GardenShopManager.createGardenShop(connectType);
                    break;
                case 2:
                    GardenShopManager.readActiveGardenShops(connectType);
                    break;
                case 3:
                    GardenShopManager.enterGardenShop(connectType);
                    break;
                case 4:
                    System.out.println("Thank you for your hard work, see you next time.");
                    System.exit(0);
                default:
                    System.out.println("Unfortunately, that option isn't included in our system.");
            }
        }
    }
}
