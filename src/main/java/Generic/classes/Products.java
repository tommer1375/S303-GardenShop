package Generic.classes;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Products {
    private final String product_id;
    private final int quantity;
    private final double total;

    public Products(Builder builder){
        this.product_id = builder.product_id;
        this.quantity = builder.quantity;
        this.total = builder.total;
    }

    public double getTotal() {
        return total;
    }
    public Document getProductDocument(){
        return new Document()
                .append("product_id", new ObjectId(this.product_id))
                .append("quantity", this.quantity)
                .append("total", this.total);
    }
    @Override
    public String toString() {
        return "\n\t\t-Product_id: " + this.product_id
                + "\n\t\t-Quantity: " + this.quantity
                + "\n\t\t-Total: " + this.total;
    }

    public static class Builder{
        private String product_id;
        private int quantity;
        private double total;

        public Builder(){

        }

        public Builder product_id(String product_id){
            this.product_id = product_id;
            return this;
        }
        public Builder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }
        public Builder total(double total){
            this.total = total;
            return this;
        }
        public Products build(){
            return new Products(this);
        }
    }
}
