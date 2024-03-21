package Generic.classes;

public class Stock {
    private String product_id;
    private final Types type;
    private double price;
    private int quantity;
    private final String quality;


    public Stock(Types type, double price, int quantity, String quality){
        this.product_id = "";
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.quality = quality;
    }
    public Stock (String product_id, Types type, double price, int quantity, String quality){
        this(type, price, quantity, quality);
        this.product_id = product_id;
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
