package Generic;

import Generic.ConnectType;
import Generic.Utilities.Input;
import Mongo.Logging.LoggingInitializer;
import Mongo.Managers.Stores.GardenShopManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

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
        while (true) {
            switch (Input.readInt("""
                    Welcome to our GardenShopManager™! Choose your option:
                    1. Create Garden Shop.
                    2. See Currently Active Garden Shops.
                    3. Enter a Garden Shop's internal management systems.
                    4. Exit.
                    """)) {
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
        private static Florist florist;
    private static final List<Ticket> salesHistory = new ArrayList<>();
Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the florist: ");
        String floristName = scanner.nextLine();
        florist = new Florist(floristName);

        while (true) {
        	System.out.println("\nFlorist Management Menu:");
            System.out.println("1 - Add Tree");
            System.out.println("2 - Add Flower");
            System.out.println("3 - Add Decoration");
            System.out.println("4 - View Stock");
            System.out.println("5 - Remove Tree");
            System.out.println("6 - Remove Flower");
            System.out.println("7 - Remove Decoration");
            System.out.println("8 - Create Sale Ticket");
            System.out.println("9 - View Sales History");
            System.out.println("10 - View Total Earnings");
            System.out.println("11.View stock with quantities");
            System.out.println("12. View total stock values ");
           
            System.out.println("13 - Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addTree(scanner);
                    break;
                case 2:
                    addFlower(scanner);
                    break;
                case 3:
                    addDecoration(scanner);
                    break;
                case 4:
                    florist.displayStock();
                    break;
                case 5:
                    removeTree(scanner);
                    break;
                case 6:
                    removeFlower(scanner);
                    break;
                case 7:
                    removeDecoration(scanner);
                    break; 
                case 8:
                    createSaleTicket(scanner);
                    break;
                case 9:
                    viewSalesHistory();
                    break;
                case 10:
                    viewTotalEarnings();
                    scanner.close();
                    return;
                case 11:  
                florist.printStockWithQuantities();
                    break;
                case 12:
                    System.out.println("Total stock value: "
                    		 + florist.calculateTotalStockValue());
                    break;
                case 13:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 13.");
            }
        }
    }
}
