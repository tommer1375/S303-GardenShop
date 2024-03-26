package Mongo.Managers.Tickets;

import Generic.Utilities.Input;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.EnteredGardenShop;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicketManager {
    public static void createTicket(){
        ObjectId store_id= EnteredGardenShop.INSTANCE.getSearchInfo().getObjectId("_id");

        List<Document> productsList = createProductList();

        double total = productsList.stream()
                .mapToDouble(product -> product.getDouble("total"))
                .sum();

        MongoDAO.INSTANCE.createTicket(new ObjectId(), store_id, productsList, total);
    }
    private static List<Document> createProductList(){
        return  Stream.generate(() -> {
                    Document product = createProduct();
                    while (product.isEmpty()) {
                        product = createProduct();
                    }
                    return product;
                })
                .takeWhile(product -> Input.readIfNo("Would you like to add another product?"))
                .collect(Collectors.toList());
    }
    private static Document createProduct(){
        ObjectId productID = new ObjectId(Input.readString(EnteredGardenShop.INSTANCE.readStockInFull()
                + "Choose which product you'd like to introduce into the ticket"));
        Document matchingStock = EnteredGardenShop.INSTANCE.getMatchingStock(productID);

        if(matchingStock.isEmpty()) {
            System.out.println("No matching item exists in stock.");
            return new Document();
        }

        int quantity = Input.readInt("How many would you like to add?");
        double total = matchingStock.getDouble("price") * quantity;

        return new Document()
                .append("product_id", matchingStock.getObjectId("product_id"))
                .append("quantity", quantity)
                .append("total", total);
    }
    public static void readPastTickets(){
        MongoDAO.INSTANCE.readPastTickets().stream()
                .map(TicketManager::ticketToString)
                .forEach(System.out::println);
    }
    public static String ticketToString(Document ticket){
        return "- Ticket_id" + ticket.getObjectId("_id")
                + "\n\tStore_id" + ticket.getString("ticket_id")
                + productsToString(ticket)
                + calculateTotal(ticket);
    }
    private static String productsToString(Document ticket){
        return ticket.getList("products", Document.class).stream()
                .map(TicketManager::productToString)
                .collect(Collectors.joining("\n\t\t- ", "\n\tStock: ", ""));
    }
    private static String productToString(Document product){
        return "[ Product_id" + product.getObjectId("product_id") + ", "
                + "Quantity: " + product.getInteger("quantity") + ", "
                + "Total: " + product.getDouble("total") + " ]";
    }
    private static String calculateTotal(Document ticket){
        return "\n\tTotal: "
                + ticket.getList("products", Document.class).stream()
                    .mapToDouble(document -> document.getDouble("total"))
                    .sum()
                + "€";
    }
}
