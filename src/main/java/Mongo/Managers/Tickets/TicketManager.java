package Mongo.Managers.Tickets;

import Mongo.Connectivity.classes.Products;
import Mongo.Connectivity.classes.Tickets;
import Mongo.Connectivity.MongoDAO;
import Mongo.Managers.Stores.EnteredGardenShop;
import Mongo.Managers.Tickets.Products.ProductManager;
import org.bson.Document;

import java.util.List;

public class TicketManager {
    public static void createTicket(){
        List<Products> productsList = ProductManager.createProductList();

        double total = productsList.stream()
                .mapToDouble(Products::getTotal)
                .sum();

        MongoDAO.INSTANCE.createTicket(EnteredGardenShop.INSTANCE.get_id(), productsList, total);
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
