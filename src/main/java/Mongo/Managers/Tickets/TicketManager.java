package Mongo.Managers.Tickets;

import Mongo.Connectivity.MongoDAO;
import org.bson.Document;

import java.util.stream.Collectors;

public class TicketManager {
    public static void createTicket(){

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
