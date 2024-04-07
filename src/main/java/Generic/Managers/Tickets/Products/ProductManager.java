package Generic.Managers.Tickets.Products;

import Generic.Managers.Stores.EnteredGardenShop;
import Generic.Utilities.Input;
import Generic.classes.Products;
import Generic.classes.Stock;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static Products createProduct(){
        String productID = Input.readString(EnteredGardenShop.INSTANCE.readStockInFull()
                + "\nChoose which product you'd like to introduce into the ticket");
        Stock matchingStock = EnteredGardenShop.INSTANCE.getMatchingStock(productID);

        if(matchingStock == null) {
            System.out.println("No matching item exists in stock.");
            return null;
        }

        int quantity = Input.readInt("How many would you like to add?");
        int updatedQuantity = matchingStock.getQuantity() - quantity;
        matchingStock.setQuantity(updatedQuantity);
        EnteredGardenShop.INSTANCE.updateItemFromStockForTicketCreation(matchingStock);

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
                .total(document.getDouble("total"))
                .build();
    }
    public static List<Products> createProductList(){
        List<Products> productsList = new ArrayList<>();

        while (true) {
            Products product = createProduct();

            if (product == null) {
                System.out.println("At least one valid product must be chosen for the ticket.");
                continue;
            }

            productsList.add(product);

            if (!Input.readBoolean("Would you like to add another product?")) {
                break;
            }
        }

        return productsList;
    }
}
