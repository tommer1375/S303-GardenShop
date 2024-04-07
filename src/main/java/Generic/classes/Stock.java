package Generic.classes;

import Generic.classes.qualities.Quality;
import Generic.classes.qualities.Types;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Stock {
    private String product_id;
    private final Types type;
    private double price;
    private int quantity;
    private final Quality quality;

    public Stock(Builder builder){
        this.product_id = builder.product_id;
        this.type = builder.type;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.quality = builder.quality;
    }

    public String getProduct_id() {
        return product_id;
    }
    public Types getType() {
        return type;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public Quality getQuality() {
        return quality;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Document getStockDocument(){
        return new Document()
                .append("product_id", new ObjectId(this.product_id))
                .append("type" , type.getMongoValue())
                .append("price", price)
                .append("quantity", quantity)
                .append(quality.getClass().getSimpleName(), quality.getName());
    }

    @Override
    public String toString() {
        return "\n\t\t- Product_id: " + this.product_id
                + "\n\t\t\tType: " + this.type.name()
                + "\n\t\t\tPrice: " + this.price + "€"
                + "\n\t\t\tQuantity: " + this.quantity
                + "\n\t\t\t" + this.quality.getClass().getSimpleName() + ": " + this.quality.getName();
    }

    public static class Builder{
        private String product_id;
        private Types type;
        private double price;
        private int quantity;
        private Quality quality;

        public Builder(){

        }

        public Builder product_id(String product_id){
            this.product_id = product_id;
            return this;
        }
        public Builder type(Types type){
            this.type = type;
            return this;
        }
        public Builder price(double price){
            this.price = price;
            return this;
        }
        public Builder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }
        public Builder quality(Quality quality){
            this.quality = quality;
            return this;
        }

        public Stock build(){
            return new Stock(this);
        }
    }
}
