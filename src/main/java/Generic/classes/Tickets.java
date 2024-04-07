package Generic.classes;

import java.util.List;
import java.util.stream.Collectors;

public class Tickets {
    private final String _id;
    private final String store_id;
    private final List<Products> productsList;
    private final double total;

    public Tickets(Builder builder){
        this._id = builder._id;
        this.store_id = builder.store_id;
        this.productsList = builder.productsList;
        this.total = builder.total;
    }

    public String get_id() {
        return _id;
    }
    public List<Products> getProductsList() {
        return productsList;
    }
    public String productsListToString(){
        return this.productsList.stream()
                .map(Products::toString)
                .collect(Collectors.joining("", "\n\tProducts bought:", ""));
    }
    @Override
    public String toString() {
        return "\nTicket " + _id
                + "\n\tStore ID: " + this.store_id
                + productsListToString()
                + "\n\tTotal: " + this.total + "€";
    }
    public static class Builder{
        private String _id;
        private String store_id;
        private List<Products> productsList;
        private double total;
        public Builder(){

        }

        public Builder _id(String _id){
            this._id = _id;
            return this;
        }
        public Builder store_id(String store_id){
            this.store_id = store_id;
            return this;
        }
        public Builder productsList(List<Products> productsList){
            this.productsList = productsList;
            return this;
        }
        public Builder total(double total){
            this.total = total;
            return this;
        }
        public Tickets build(){
            return new Tickets(this);
        }
    }
}
