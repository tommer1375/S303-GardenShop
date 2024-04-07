package Generic.Managers.Tickets;

import Generic.Managers.Stores.EnteredGardenShop;
import Generic.classes.Products;
import Generic.classes.Tickets;
import Generic.Managers.Tickets.Products.ProductManager;
import org.bson.Document;

import java.util.List;

public class TicketManager {
    public static void createTicket(){
        List<Products> productsList = ProductManager.createProductList();

        double total = productsList.stream()
                .mapToDouble(Products::getTotal)
                .sum();

        EnteredGardenShop.INSTANCE.createTicket(productsList, total);
    }
    public static Tickets createTicketFromDocument(Document document){
        List<Products> productsList = document.getList("products", Document.class).stream()
                .map(ProductManager::createProductFromDocument)
                .toList();

        double total = productsList.stream()
                .mapToDouble(Products::getTotal)
                .sum();

        return new Tickets.Builder()
                ._id(document.getObjectId("_id").toString())
                .store_id(document.getObjectId("store_id").toString())
                .productsList(productsList)
                .total(total)
                .build();
    }
    public static void readPastTickets(){
        System.out.println(EnteredGardenShop.INSTANCE.readTickets());
    }
}
