package Generic.classes;

import java.util.List;

public class Tickets {
    private final String _id;
    private final String store_id;
    private final List<Products> productsList;
    private final double total;

    public Tickets(String _id, String store_id, List<Products> productsList, double total){
        this._id = _id;
        this.store_id = store_id;
        this.productsList = productsList;
        this.total = total;
    }

    public String getProductsList(){
        String textToReturn = "";
        for (Products product : productsList){
            textToReturn = textToReturn.concat("\t\tName: " + "\tQuantity: " + "\tPrice: ");
        }
//        Implement code to get the product's name, and price and place it on a string to send back
        return textToReturn;
    }
    @Override
    public String toString() {
        return "Ticket " + _id
                + "\n\tStore ID: " + this.store_id
                + "\n\tProducts bought: " + getProductsList()
                + "\n\tTotal: " + this.total + "€";
    }
}
