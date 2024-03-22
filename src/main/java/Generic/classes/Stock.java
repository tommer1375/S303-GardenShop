package Generic.classes;

import Mongo.Managers.Stores.stock.qualities.Quality;
import Mongo.Managers.Stores.stock.qualities.Types;

public class Stock {
    private String product_id;
    private final Types type;
    private double price;
    private int quantity;
    private final Quality quality;


    public Stock(Types type, double price, int quantity, Quality quality){
        this.product_id = "";
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.quality = quality;
    }
    public Stock (String product_id, Types type, double price, int quantity, Quality quality){
        this(type, price, quantity, quality);
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
//        Setup how to get the price from product_id and multiply by current quantity to set the total.
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "\n- Product_id: " + this.product_id
                + "\n\nType: " + this.type.name()
                + "\n\nPrice: " + this.price + "€"
                + "\n\nQuantity: " + this.quantity
//                Remember to code it, so it follows the structure when it's being made lmao
                + "\n\n" + this.quality;
    }
}
