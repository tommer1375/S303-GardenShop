package Generic.classes;

public class Stock {
    private final String product_id;
    private int quantity;
    private double total;

    public Stock (String product_id, int quantity, double total){
        this.product_id = product_id;
        this.quantity = quantity;
        this.total = total;
    }

    public void setQuantity(int quantity) {
//        Setup how to get the price from product_id and multiply by current quantity to set the total.
        this.quantity = quantity;
    }
}
