
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Florist florist;
    private static final List<Ticket> salesHistory = new ArrayList<>();

    public static void main(String[] args) {
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

    private static void addTree(Scanner scanner) {
        System.out.print("Enter tree name: ");
        String name = scanner.nextLine();
        System.out.print("Enter tree height: ");
        double height = scanner.nextDouble();
        System.out.print("Enter tree price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        Tree tree = new Tree(name, price, height);
        florist.addProduct(tree);
        System.out.println("Tree added to stock.");
    }

    private static void addFlower(Scanner scanner) {
        System.out.print("Enter flower name: ");
        String name = scanner.nextLine();
        System.out.print("Enter flower color: ");
        String color = scanner.nextLine();
        System.out.print("Enter flower price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        Flower flower = new Flower(name, price, color);
        florist.addProduct(flower);
        System.out.println("Flower added to stock.");
    }

    private static void addDecoration(Scanner scanner) {
        System.out.print("Enter decoration name: ");
        String name = scanner.nextLine();
        System.out.println("Select decoration material (1 - Wood, 2 - Plastic):");
        int materialChoice = scanner.nextInt();
        scanner.nextLine(); 
        Decoration.Material material = (materialChoice == 1) ? Decoration.Material.WOOD : Decoration.Material.PLASTIC;
        System.out.print("Enter decoration price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        Decoration decoration = new Decoration(name, price, material);
        florist.addProduct(decoration);
        System.out.println("Decoration added to stock.");
    }

    private static void removeTree(Scanner scanner) {
        System.out.print("Enter tree name to remove: ");
        String name = scanner.nextLine();
        boolean removed = florist.getStock().removeIf(product -> product instanceof Tree && product.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Tree removed.");
        } else {
            System.out.println("Tree not found.");
        }
    }

    private static void removeFlower(Scanner scanner) {
        System.out.print("Enter flower name to remove: ");
        String name = scanner.nextLine();
        boolean removed = florist.getStock().removeIf(product -> product instanceof Flower && product.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Flower removed.");
        } else {
            System.out.println("Flower not found.");
        }
    }

    private static void removeDecoration(Scanner scanner) {
        System.out.print("Enter decoration name to remove: ");
        String name = scanner.nextLine();
        boolean removed = florist.getStock().removeIf(product -> product instanceof Decoration && product.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Decoration removed.");
        } else {
            System.out.println("Decoration not found.");
        }
    }

    private static void createSaleTicket(Scanner scanner) {
        Ticket ticket = new Ticket();
        System.out.println("Adding products to the ticket. Enter 'done' to finish.");
        while (true) {
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            if ("done".equalsIgnoreCase(name)) break;
            Product product = florist.getProductByName(name);
            if (product != null) {
                ticket.addProduct(product);
                florist.removeProduct(name);
                System.out.println("Product added to ticket.");
            } else {
                System.out.println("Product not found.");
            }
        }
        salesHistory.add(ticket);
        System.out.println("Sale ticket created.");
    }

    private static void viewSalesHistory() {
        System.out.println("Sales History:");
        for (Ticket ticket : salesHistory) {
            System.out.println(ticket);
        }
    }
    
    private static void viewTotalEarnings() {
        double totalEarnings = 0.0; 
        for (Ticket ticket : salesHistory) {
             totalEarnings += ticket.getTotal();
        }
 System.out.println("Total earnings: " + totalEarnings + " Euros.");
    }
}
