package Mongo.Managers.Tickets.Products;

import Generic.Utilities.Input;
import Generic.classes.Products;
import Generic.classes.Stock;
import Mongo.Managers.Stores.EnteredGardenShop;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductManager {
    private static Products createProduct(){
        String productID = Input.readString(EnteredGardenShop.INSTANCE.readStockInFull()
                + "Choose which product you'd like to introduce into the ticket");
        Stock matchingStock = EnteredGardenShop.INSTANCE.getMatchingStockID(productID);

        if(matchingStock == null) {
            System.out.println("No matching item exists in stock.");
            return null;
        }

        int quantity = Input.readInt("How many would you like to add?");
        double total = matchingStock.getPrice() * quantity;

        return new Products.Builder()
                .product_id(productID)
                .quantity(quantity)
                .total(total)
                .build();
    }
    public static Products createProductFromDocument(Document document){
        return new Products.Builder()
                .product_id(document.getObjectId("product_id").toString())
                .quantity(document.getInteger("quantity"))
                .total(document.getInteger("total"))
                .build();
    }
    public static List<Products> createProductList(){
        return  Stream.generate(() -> {
                    Products product = createProduct();
                    while (product == null) {
                        product = createProduct();
                    }
                    return product;
                })
                .takeWhile(product -> Input.readIfNo("Would you like to add another product?"))
                .collect(Collectors.toList());
    }
}
